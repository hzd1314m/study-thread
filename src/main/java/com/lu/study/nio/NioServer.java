package com.lu.study.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * <b>System：</b>study-thread<br/>
 * <b>Title：</b>NioServer.java<br/>
 * <b>Description：</b> 对功能点的描述<br/>
 * <b>@author： </b>raoluping<br/>
 * <b>@date：</b>2018/3/27 13:29<br/>
 * <b>@version：</b> 1.0.0.0 <br/>
 * <b>Copyright (c) 2017 ASPire Tech.</b>
 */
public class NioServer {

    // nio的选择器
    private Selector selector;

    // 读缓冲
    private ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);

    // 写缓冲
    private ByteBuffer sendByteBuffer = ByteBuffer.allocate(1024);

    // 读写数据内容
    private byte[] bytes;

    public void start() throws IOException {
        // 打开服务器套接字通道
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 服务器配置为非阻塞
        channel.configureBlocking(false);
        // 进行服务的绑定
        channel.bind(new InetSocketAddress("localhost", 8001));
        // 通过open()方法找到Selector
        selector = Selector.open();
        // 注册到selector，等待连接
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (!Thread.currentThread().isInterrupted()) {
            // 选择一组键，其相应的通道已为 I/O 操作准备就绪。
            // 此方法执行处于阻塞模式的选择操作。
            selector.select();
            // 返回此选择器的已选择键集。
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeySet.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                } else if (key.isWritable()) {
                    write(key);
                }
                // 该事件已经处理，可以丢弃
                it.remove();
            }
        }
    }

    /**
     * 服务端发送消息
     * 
     * @param key
     * @throws IOException
     */
    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        Random random = new Random();
        String context = "neinongwei " + random.nextInt(100000) + "\n";
        bytes = context.getBytes();
        System.out.println(" 服务器端发送给客户端数据--:" + new String(bytes));

        // 先清空缓冲
        sendByteBuffer.clear();
        // 把服务端的数据丢到缓冲里
        sendByteBuffer.put(bytes);
        // 复位
        sendByteBuffer.flip();

        // 服务端数据经过通道发送给客户端
        channel.write(sendByteBuffer);
        channel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 读取客户端的消息
     * 
     * @param key
     * @throws IOException
     */
    private void read(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();

        // Clear out our read buffer so it's ready for new data
        readByteBuffer.clear();
        // readBuffer.flip();

        // 读取服务器发送来的数据到缓冲区中
        int count = client.read(readByteBuffer);
        if (count > 0) {

            System.out.println("服务器端接受客户端数据--:" + new String(readByteBuffer.array(), 0, count));
        }
        client.register(selector, SelectionKey.OP_WRITE);

    }

    private void accept(SelectionKey key) throws IOException {
        // 从选择器中获取通道
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();

        // 通信通道
        SocketChannel clientChannel = channel.accept();

        // 设置为非阻塞
        clientChannel.configureBlocking(false);
        // 在选择器上注册读事件，即客户端监听服务的写事件
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("a new client build new concat" + clientChannel.getRemoteAddress());
    }


    public static void main(String[] args) throws IOException {
        new NioServer().start();
    }
}
