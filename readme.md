## FlipImageView
继承自 ImageView ，能够实现 ImageView 的翻转效果，一般用于勾选某个 ImageView 之后的变化等。

### 引入方法

#### Gradle

在应用的`build.gradle`文件中：

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

在模块的`build.gradle`文件中：

```groovy
dependencies {
	        implementation 'com.github.ruoyewu:FlipImageView:1.0.0'
}
```

#### Maven

分别引入加载地址和加载库文件：

```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.ruoyewu</groupId>
    <artifactId>FlipImageView</artifactId>
    <version>1.0.0</version>
</dependency>
```



### 使用方法

```xml
<com.wuruoye.flipimageview.FlipImageView
        android:id="@+id/fiv"
        app:flipDirection="LR"
        app:flipDuration="300"
        app:flipBgColor="@color/colorPrimary"
        app:flipCheckColor="@color/colorAccent"
        app:flipScale="0.5"
        app:flipCheckDrawable="@drawable/ic_check"
        android:src="@drawable/ic_free_breakfast"
        android:layout_centerInParent="true"
        android:layout_width="50dp"
        android:layout_height="50dp" />
```

1. flipDirect：{LR, UD} 分别为左右翻转和上下翻转
2. flipDuration：动画持续时长
3. flipBgColor：翻转后的背景颜色
4. flipCheckColor：翻转后的勾选颜色
5. flipScale：翻转后勾选图片的缩放程序
6. flipCheckDrawable：翻转后勾选图片