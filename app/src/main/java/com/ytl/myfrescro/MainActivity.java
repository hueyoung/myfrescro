package com.ytl.myfrescro;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
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
                                "http://img2.imgtn.bdimg.com/it/u=2925868644,1284730160&fm=21&gp=0.jpg",
                                "http://bbs.static.coloros.com/data/attachment/forum/201503/09/182142idaabaxxa60bneqm.jpg",//4k
                                "data:image/png;base64,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"
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
        Uri uri =  Uri.parse(url[3]); //这里需要注意用了controller之后就不能这样单独设置了 方式二
        //simpleDraweeView.setImageURI(uri);

        //设置渐进式图片
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .setResizeOptions(new ResizeOptions(2000, 2000))//图片resize,实验大图2500 * 2500没问题，小图10 * 10按xml显示变模糊，单位 px  经实验设置有效避免oom,可以将值设置为当前控件最大像素，如果真实图片小于设置图片大小不会加大内存和使图片变模糊
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
