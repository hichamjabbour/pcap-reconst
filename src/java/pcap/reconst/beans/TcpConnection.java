package pcap.reconst.beans;

import pcap.reconst.beans.TcpPacket;
import pcap.reconst.beans.TestTcpPacket;

import java.net.InetAddress;

public class TcpConnection implements Comparable {
    private InetAddress srcIp;
    private int srcPort;
    private InetAddress dstIp;
    private int dstPort;

    public TcpConnection(InetAddress sourceIP, short sourcePort, InetAddress destinationIP, short destinationPort) {
        this(new TestTcpPacket(sourceIP, sourcePort, destinationIP, destinationPort));
        srcIp = sourceIP;
        dstIp = destinationIP;
        srcPort = sourcePort;
        dstPort = destinationPort;
    }

    public TcpConnection(TcpPacket packet) {
        srcIp = packet.getSourceIP();
        dstIp = packet.getDestinationIP();
        srcPort = packet.getSourcePort();
        dstPort = packet.getDestinationPort();
    }

    public InetAddress getSrcIp() {
        return srcIp;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public InetAddress getDstIp() {
        return dstIp;
    }

    public int getDstPort() {
        return dstPort;
    }

    //ensures both request and response are reconstructed together
    public boolean equals(Object obj) {
        if (!(obj instanceof TcpConnection))
            return false;

        TcpConnection con = (TcpConnection) obj;

        return ((con.srcIp.equals(srcIp)) && (con.srcPort == srcPort) && (con.dstIp.equals(dstIp)) && (con.dstPort == dstPort)) ||
                ((con.srcIp.equals(dstIp)) && (con.srcPort == dstPort) && (con.dstIp.equals(srcIp)) && (con.dstPort == srcPort));

    }

    public TcpConnection() {
    }

    public int hashCode() {
        return ((srcIp.hashCode() ^ srcPort) ^
                ((dstIp.hashCode() ^ dstPort)));
    }

    @Override
    public String toString() {
        return String.format("%s.%s-%s.%s", srcIp.toString().replace("/", ""), srcPort, dstIp.toString().replace("/", ""), dstPort);
    }

/*
    public String getFileName(String path) {
        return String.format("%s%s.data", path, toString());
    }

*/
    public int compareTo(Object o) {
        if (!(o instanceof TcpConnection)) {
            return -1;
        }

        TcpConnection other = (TcpConnection) o;
        if (this.equals(other)) {
            return 0;
        }

        if (getSrcPort() != 80 && other.getSrcPort() != 80) {
            return getSrcPort() - other.getSrcPort();
        } else if (getDstPort() != 80 && other.getDstPort() != 80) {
            return getDstPort() - other.getDstPort();
        } else {
            return getDstPort() * 2 + getSrcPort() - other.getSrcPort() * 2 + other.getDstPort();
        }
    }
}
