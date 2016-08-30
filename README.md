# myfrescro
frescro实战
* 1.加载特别特别大的图片时最容易导致这种情况。如果你加载的图片比承载的View明显大出太多，那你应该考虑将它Resize一下。
 * 2.不要使用 ScrollViews
 * 3.本地图片复用可用于拍照上传，一图配多url，优先加载本地//数组的顺序为优先顺序
 * 4.缩略图，加载大图之前先加载模糊缩略图，同样需要配置两个url一个高清一个不高清
 * 5.滑动时停住加载及停止滑动开始加载 Fresco.getImagePipeline().pause();Fresco.getImagePipeline().resume();
 * 6.硬盘缓存路径？
 * 7.想Resize一下?
 * 8.frescro对JPEG貌似更友好一些，对针对网络，本地则一次解码没有渐进
 * 9.不支持wrap_content
 *10.Android 无法绘制长或宽大于2048像素的图片这是由OpenGL渲染系统限制的
