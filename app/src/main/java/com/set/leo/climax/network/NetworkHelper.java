package com.set.leo.climax.network;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Author    LeoCheung
 * Email     leocheung4ever@gmail.com
 * Description  Get the network interfaces and associated addresses for this host
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/25     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class NetworkHelper {

    public static void main(String[] args) {
        try {
            //获取主机的网络接口列表
            //静态方法getNetworkInterfaces()返回一个列表,其中包含了该主机每一个接口所对应的NetworkInterface类实例
            Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
            //空列表检测
            //通常情况下,即使主机没有任何其他网络连接,回环接口也总是存在的,因此,只要当一个主机根本没有网络子系统时,列表检测才为空
            if (interfaceList == null) {
                System.out.println("-- No interfaces found --");
            } else {
                //获取并打印出列表中每个接口的地址
                while (interfaceList.hasMoreElements()) {
                    NetworkInterface iface = interfaceList.nextElement();
                    //打印接口名
                    //getName()方法为接口返回一个本地名称.接口的本地名称通常由字母与数字的联合组成,代表了接口的类型和具体实例
                    System.out.println("Interface" + iface.getName() + ":");
                    //获取与接口相关联的地址
                    //getInetAddress()方法返回了另一个Enumeration类对象,其中包含了InetAddress类的实例,即该接口所关联的每一个地址.
                    //根据主机的不同配置,这个地址列表可能只包含IPv4或IPv6地址,或者包含了两种类型地址的混合列表
                    Enumeration<InetAddress> addrList = iface.getInetAddresses();
                    //空列表检测
                    if (!addrList.hasMoreElements()) {
                        System.out.println("\t(No addresses for this interface)");
                    }
                    //列表的迭代,打印出每个地址
                    //对每个地址实例进行检测以判断其属于哪个IP地址子类
                    //(目前InetAddress的子类只有上面列出的那些,但可以想象到,将来也许还会有其他子类)
                    //InetAddress类的getHostAddress()方法返回一个字符串来代表主机的数字型地址.
                    //不同类型的地址对应了不同的格式: IPv4是点分形式,IPv6是冒号分隔的16进制形式.
                    while (addrList.hasMoreElements()) {
                        InetAddress address = addrList.nextElement();
                        System.out.print("\tAddress"
                                + ((address instanceof Inet4Address ? "(v4)"
                                : (address instanceof Inet6Address ? "(v6)" : "(?)")
                        )));
                        System.out.println(":" + address.getHostAddress());
                    }
                }
            }
        } catch (SocketException se) {
            //捕获异常
            //对getNetworkInterfaces()方法的调用将会抛出SocketException异常
            System.out.println("Error getting network interfaces:" + se.getMessage());
        }

        //Get name(s)/address(es) of hosts given on command line
        //获取从命令行输入的每个参数锁对应的主机名和地址
        for (String host : args) {
            try {
                System.out.println(host + ":");
                InetAddress[] addressList = InetAddress.getAllByName(host);
                //获取给定主机/地址的相关地址列表
                for (InetAddress address : addressList) {
                    //迭代列表,打印出列表中的每一项
                    //对于列表中的每个主机,通过调用getHostName()方法来的打印主机名,并把调用getHostAddress()方法所获得的数字型地址打印在主机名后面
                    System.out.println("\t" + address.getHostName() + "/" + address.getHostAddress());
                }
            } catch (UnknownHostException e) {
                System.out.println("\tUnable to find address for " + host);
            }
        }
    }
}
































