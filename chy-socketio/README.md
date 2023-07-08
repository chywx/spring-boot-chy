## socket.io

## 连接
ws://127.0.0.1:2468/socketio/?EIO=3&transport=websocket

需要注意，有个/斜杠

## 客户端测试
提供了java工具类和html测试页面

## 关于context路径问题
比较奇怪，毕竟websocket和socket.io还是有区别的
测试的时候还是把context加上

## websocket和socket.io之间的区别
一、性质不同

1、websocket：websocket是一种让客户端和服务器之间能进行双向实时通信的技术。

2、socket.io：socket.io是将WebSocket、AJAX和其它的通信方式全部封装成了统一的通信接口。

二、兼容不同

1、websocket：在使用websocket时，，虽然主流浏览器都已经支持，但仍然可能有不兼容的情况。

2、socket.io：在使用socket.io时，不用担心兼容问题，底层会自动选用最佳的通信方式。


三、用途不同

1、websocket：websocket适合用于client和基于node搭建的服务端使用。

2、socket.io：socket.io适合进行服务端和客户端双向数据通信