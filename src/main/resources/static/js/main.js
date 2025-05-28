// 全局配置
const API_BASE_URL = '/api';
const TOKEN_KEY = 'token';

// 检查用户登录状态
function checkLoginStatus() {
    const token = localStorage.getItem(TOKEN_KEY);
    if (token) {
        $('#notLoginSection').addClass('d-none');
        $('#loginSection').removeClass('d-none');
        // 获取用户信息
        getUserInfo();
    } else {
        $('#notLoginSection').removeClass('d-none');
        $('#loginSection').addClass('d-none');
    }
}

// 获取用户信息
function getUserInfo() {
    $.ajax({
        url: `${API_BASE_URL}/user/info`,
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        },
        success: function(response) {
            if (response.code === 200) {
                $('#username').text(response.data.username);
                // 更新用户类型
                if (response.data.userType === 1) {
                    $('.vip-only').removeClass('d-none');
                }
            }
        },
        error: function() {
            // token可能已过期，清除本地存储
            logout();
        }
    });
}

// 退出登录
function logout() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem('username');
    window.location.href = '/';
}

// 显示提示消息
function showToast(message, type = 'info') {
    const toast = `
        <div class="toast align-items-center text-white bg-${type} border-0" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `;
    
    const toastContainer = $('<div class="toast-container position-fixed bottom-0 end-0 p-3"></div>');
    toastContainer.append(toast);
    $('body').append(toastContainer);
    
    const toastElement = toastContainer.find('.toast');
    const bsToast = new bootstrap.Toast(toastElement);
    bsToast.show();
    
    // 自动移除toast
    toastElement.on('hidden.bs.toast', function() {
        toastContainer.remove();
    });
}

// 格式化日期
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
}

// 格式化数字（如播放量）
function formatNumber(num) {
    if (num >= 1000000) {
        return (num / 1000000).toFixed(1) + 'M';
    }
    if (num >= 1000) {
        return (num / 1000).toFixed(1) + 'K';
    }
    return num.toString();
}

// 检查VIP权限
function checkVipPermission() {
    const token = localStorage.getItem(TOKEN_KEY);
    if (!token) {
        showToast('请先登录', 'warning');
        setTimeout(() => {
            window.location.href = '/user/login';
        }, 1000);
        return false;
    }
    
    // 从session中获取用户类型
    const userType = $('#userType').val();
    if (userType !== '1') {
        showToast('该功能仅限VIP会员使用', 'warning');
        setTimeout(() => {
            window.location.href = '/user/vip';
        }, 1000);
        return false;
    }
    
    return true;
}

// 添加请求拦截器
$.ajaxSetup({
    beforeSend: function(xhr) {
        const token = localStorage.getItem(TOKEN_KEY);
        if (token) {
            xhr.setRequestHeader('Authorization', `Bearer ${token}`);
        }
    },
    error: function(xhr) {
        if (xhr.status === 401) {
            showToast('登录已过期，请重新登录', 'warning');
            setTimeout(() => {
                logout();
            }, 1000);
        } else if (xhr.status === 403) {
            showToast('无权限访问', 'danger');
        } else {
            showToast('请求失败，请稍后重试', 'danger');
        }
    }
});

// 页面加载完成后执行
$(document).ready(function() {
    // 检查登录状态
    checkLoginStatus();
    
    // 初始化所有工具提示
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // 初始化所有弹出框
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function(popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
}); 