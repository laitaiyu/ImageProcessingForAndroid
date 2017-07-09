# ImageProcessingForAndroid

![image](https://github.com/laitaiyu/ImageProcessingForAndroid/blob/master/Article_Comic.gif)
![image](https://github.com/laitaiyu/ImageProcessingForAndroid/blob/master/Article_Gray.gif)

# Introduction

Sometimes, we just feel fun to take a picture. Let’s take a different funny picture. We can develop by self. To design gray and comic style picture, it is not hard. You just need to own an Android phone and you can establish a funny camera app. Normally, you will encounter auto focus when you take a picture. This problem can be solved in this study. Firstly, while we can waiting the auto focus to finish when we take a picture. That is why need to overwrite auto focus method to help us take a clear picture. Secondly, while the image processing had compute when we need to transform raw data to bitmap format. We used to bitmap factory to help us do this transform. Thirdly, there are two image processing that was chosen by users. The gray scale and comic effect in the this study. The gray scale is very simple that is just getting R, G and B of amount and then divide by three. That is your new pixels of gray scale, But we still need to know how to get R, G and B of channel respectively. We are getting a pixel value after use the Color method to help us get R, G and B of values. There is a difficult convolution processing about comic effect. The comic progress are two processing, including Sobel filter and ordered dither processing. We need to combine the two results of the image processing to establish a new image, that is called comic effect.
If you want to develop more image effect, you could use this code of architecture. To keep developing your new idea. Because there are preparing auto focus and take a picture of callback function. You just follow this architecture and then to add new image effect.  
In this study, this app will open your camera before you choice image effect and then you have chosen an effect gray scale or comic. You can press 'take a picture' button and then you can watch a processed image. Such as those figures.
To develop this app just only two files, It is very simple, that is why I wrote this article. Because to help beginners in the camera app development. All code presents this article. I need to, you're a little patient to read. And then you will find out that so easy.


# Background

# Equipment
Operation System: Android 4.1.2
Development Utility: Eclipse ADT

# Using the code

Please download through by GitHub.

# Exception

1. Fot debug, you should enable USB debug with your android phone.


# Acknowledge

Thank you (Android, Eclipse) very much for this great development utility.
