# MovieMate API 接口文档

## 一、接口规范

### 1. 基础信息
- 基础URL: `http://localhost:8080/api`
- 请求方式: REST风格
- 数据格式: JSON
- 字符编码: UTF-8

### 2. 通用响应格式
```json
{
    "code": 200,           // 状态码
    "message": "success",  // 响应消息
    "data": {}            // 响应数据
}
```

### 3. 状态码说明
| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 二、接口详情

### 1. 用户模块

#### 1.1 用户注册
- 请求路径：`/user/register`
- 请求方式：POST
- 请求参数：
```json
{
    "username": "string",  // 用户名，6-20位字母/数字
    "password": "string",  // 密码，8-20位，含大小写字母+数字
    "email": "string",     // 邮箱
    "phone": "string"      // 手机号（选填）
}
```
- 响应示例：
```json
// 成功
{
    "code": 200,
    "message": "注册成功",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "userType": 0
    }
}

// 失败
{
    "code": 400,
    "message": "用户名已存在",
    "data": null
}
```

#### 1.2 用户登录
- 请求路径：`/user/login`
- 请求方式：POST
- 请求参数：
```json
{
    "username": "string",
    "password": "string"
}
```
- 响应示例：
```json
// 成功
{
    "code": 200,
    "message": "登录成功",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "userType": 1,
        "username": "test_user"
    }
}

// 失败
{
    "code": 401,
    "message": "用户名或密码错误",
    "data": null
}
```

### 2. 电影模块

#### 2.1 获取电影列表
- 请求路径：`/movie/list`
- 请求方式：GET
- 请求参数：
```
type: string    // 电影类型（选填）
region: string  // 地区（选填）
page: number    // 页码，默认1
size: number    // 每页数量，默认10
```
- 响应示例：
```json
// 成功
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

#### 2.2 获取电影详情
- 请求路径：`/movie/detail/{id}`
- 请求方式：GET
- 响应示例：
```json
// 成功
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

### 3. 支付模块

#### 3.1 创建VIP订单
- 请求路径：`/payment/create-vip-order`
- 请求方式：POST
- 请求参数：
```json
{
    "vipDuration": 3  // VIP时长（月）
}
```
- 响应示例：
```json
// 成功
{
    "code": 200,
    "message": "订单创建成功",
    "data": {
        "orderId": "202403150001",
        "amount": 29.9,
        "payUrl": "https://openapi.alipaydev.com/..."
    }
}

// 失败
{
    "code": 400,
    "message": "订单创建失败",
    "data": null
}
```

### 4. 排行榜模块

#### 4.1 获取热门电影排行
- 请求路径：`/rank/hot`
- 请求方式：GET
- 请求参数：
```
period: string  // 周期（week/month/all）
```
- 响应示例：
```json
// 成功
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "rank": 1,
            "movieId": 1,
            "movieName": "电影名称",
            "viewCount": 10000,
            "score": 8.5
        }
    ]
}
```

## 三、错误码说明

### 1. 通用错误码
| 错误码 | 说明 |
|--------|------|
| 1001 | 参数校验失败 |
| 1002 | 系统异常 |
| 1003 | 服务不可用 |

### 2. 业务错误码
| 错误码 | 说明 |
|--------|------|
| 2001 | 用户名已存在 |
| 2002 | 邮箱已被注册 |
| 2003 | 密码强度不足 |
| 2004 | VIP权限不足 |
| 2005 | 订单创建失败 |
| 2006 | 支付失败 | 