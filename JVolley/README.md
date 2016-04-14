### 源码修改部分：###

+ com.android.volley
        
    - Response.java
            
        interface Listener<T> 接口添加一个 onResponse(T response, Map<String, String> setCookies, String cookies) 方法, 该方法返回Headers的所有Set-Cookie和Cookies
            
+ com.android.volley.toolbox
        
    - HurlStack.java
            
        修改 performRequest(Request<?> request, Map<String, String> additionalHeaders) 方法，获取多个Set-Cookie
        
    - ImageLoader.java
            
        在 makeImageRequest(String requestUrl, int maxWidth, int maxHeight, ScaleType scaleType, final String cacheKey) 的返回结果监听里面添加 onResponse(Bitmap response, Map<String, String> setCookies, String cookies) 方法
        
    - RequestFuture.java
            
        实现 onResponse(Bitmap response, Map<String, String> setCookies, String cookies) 方法
            
### 添加的类###
        
+ com.android.jvolley.utils
            
    - BaseRequest.java
            
        请求队列的基类
            
    - CallBackListener.java
                
        回调监听类
            
    - GsonRequest.java
            
        Gson 请求类，继承 Request，数据解析、数据分发、设置 Headers、设置 Body、设置请求参数等，都在这个类里面实现
            
    - GsonUtils.java
            
        Gson 工具类
            
    - JGsonRequest.java
            
        Gson 请求类，继承 BaseRequest，调用该类的 get 或者 post 实现 GET 或者 POST 请求
            
    - JImageCache.java
            
        自定义图片缓存器，实现 ImageLoder.ImageCache 实现其中的方法
            
    - JImageLoader.java
            
        图片加载类
            
    - LogUtils.java
            
        自定义日志工具类
    
    - RequestQueueSington.java
            
        RequestQueue 单例模式