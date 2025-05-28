# MovieMate API 接口说明文档

## 一、通用说明

### 1. 基础信息
- 基础URL: `http://localhost:8080/api`
- 数据格式: JSON
- 认证方式: JWT Token (在请求头中携带 `Authorization: Bearer {token}`)

### 2. 响应格式
```json
{
    "code": 200,       // 状态码：200成功，400参数错误，401未登录，403无权限，500服务器错误
    "message": "success", // 响应消息
    "data": {}         // 响应数据
}
```

## 二、用户模块接口

### 1. 用户注册
- 请求路径：`/user/register`
- 请求方法：POST
- 请求参数：
```json
{
    "username": "string", // 用户名，6-20位字母/数字
    "password": "string", // 密码，8-20位，含大小写字母+数字
    "email": "string",    // 邮箱
    "phone": "string"     // 手机号（选填）
}
```
- 响应示例：
```json
{
    "code": 200,
    "message": "注册成功",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "userType": 0
    }
}
```

### 2. 用户登录
- 请求路径：`/user/login`
- 请求方法：POST
- 请求参数：
```json
{
    "username": "string",
    "password": "string"
}
```
- 响应示例：
```json
{
    "code": 200,
    "message": "登录成功",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "userType": 1,
        "username": "test_user"
    }
}
```

## 三、电影模块接口

### 1. 获取电影列表
- 请求路径：`/movie/list`
- 请求方法：GET
- 请求参数：
  - type: 电影类型（选填）
  - region: 地区（选填）
  - page: 页码（默认1）
  - size: 每页数量（默认10）
- 响应示例：
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 100,
        "list": [
            {
                "id": 1,
                "movieName": "电影名称",
                "movieType": "动作",
                "region": "美国",
                "score": 8.5,
                "viewCount": 10000,
                "isVip": 0,
                "coverImg": "http://..."
            }
        ]
    }
}
```

### 2. 获取电影详情
- 请求路径：`/movie/detail/{id}`
- 请求方法：GET
- 响应示例：
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1,
        "movieName": "电影名称",
        "movieType": "动作",
        "region": "美国",
        "director": {
            "id": 1,
            "directorName": "导演名"
        },
        "actors": [
            {
                "id": 1,
                "actorName": "演员名"
            }
        ],
        "releaseTime": "2024-01-01",
        "score": 8.5,
        "viewCount": 10000,
        "isVip": 0,
        "coverImg": "http://...",
        "description": "电影简介"
    }
}
```

## 四、支付模块接口

### 1. 创建VIP订单
- 请求路径：`/payment/create-vip-order`
- 请求方法：POST
- 请求参数：
```json
{
    "vipDuration": 3  // VIP时长（月）
}
```
- 响应示例：
```json
{
    "code": 200,
    "message": "订单创建成功",
    "data": {
        "orderId": "202403150001",
        "amount": 29.9,
        "payUrl": "https://openapi.alipaydev.com/..."
    }
}
```

## 五、排行榜模块接口

### 1. 获取热门电影排行
- 请求路径：`/rank/hot`
- 请求方法：GET
- 请求参数：
  - period: 周期（week/month/all）
- 响应示例：
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "list": [
            {
                "id": 1,
                "movieName": "电影名称",
                "score": 8.5,
                "viewCount": 10000,
                "heat": 8500
            }
        ]
    }
}
```

## 六、主创模块接口

### 1. 获取演员作品
- 请求路径：`/actor/{id}/movies`
- 请求方法：GET
- 响应示例：
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "actor": {
            "id": 1,
            "actorName": "演员名",
            "gender": 0,
            "birthDate": "1990-01-01",
            "intro": "演员简介",
            "imageUrl": "http://..."
        },
        "movies": [
            {
                "id": 1,
                "movieName": "电影名称",
                "movieType": "动作",
                "score": 8.5,
                "coverImg": "http://..."
            }
        ]
    }
}
```

### 2. 获取导演作品
- 请求路径：`/director/{id}/movies`
- 请求方法：GET
- 响应格式同演员作品接口 