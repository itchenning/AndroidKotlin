# 通用配置以及大多数第三方配置依赖库：com.blankj:free-proguard
### 本应用配置开始 ###
# 自定义View, model和数据库。
-keep class com.terry.baseproject.model.**{*;}
-keep class com.terry.baseproject.view.**{*;}
-keep class com.terry.baseproject.database.table.**{*;}
### 本应用配置结束 ###
