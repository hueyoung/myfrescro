package com.ytl.myfrescro;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
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
 */
public class MainActivity extends AppCompatActivity
{
    private SimpleDraweeView simpleDraweeView;
    private String url[] = {
                                "http://scimg.jb51.net/allimg/160815/103-160Q51G213930.jpg",
                                "http://img2.imgtn.bdimg.com/it/u=2925868644,1284730160&fm=21&gp=0.jpg"
                            };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initApplicaton();
        setContentView(R.layout.activity_main);
        initView();
     }
    //初始化
    private void initView()
    {
        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.my_image_view);
    }

    private void initApplicaton()
    {
        //模拟appliaction初始化frescro
        Fresco.initialize(this);
    }

    public void btnLoad(View v)
    {
        //如果你需要对加载显示的图片做更多的控制和定制
      /*  DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url[1])
                .setTapToRetryEnabled(true)//设置可以加载失败点击重新加载xml设置重新加载图片以后需此处设置
                .build();*///方式一
        Uri uri =  Uri.parse(url[0]); //这里需要注意用了controller之后就不能这样单独设置了 方式二
        //simpleDraweeView.setImageURI(uri);

        //设置渐进式图片
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                //.setResizeOptions(new ResizeOptions(200, 200))//图片resize,实验大图2500 * 2500没问题，小图10 * 10按xml显示变模糊，单位 px
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .setAutoPlayAnimations(true)//动画自动播放gif动画播放
                .build();

        simpleDraweeView.setController(controller);
    }

    //清除所有缓存
    public void btnClear(View v)
    {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();

        // combines above two lines
        imagePipeline.clearCaches();
    }
}
