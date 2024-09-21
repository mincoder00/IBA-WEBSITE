    function isUserLoggedIn() {

        return !!localStorage.getItem('access_token');
    }

document.addEventListener('DOMContentLoaded', function() {
    const authLink = document.getElementById('authLink');

    if (isUserLoggedIn()) {
        authLink.innerHTML = '<a class="nav-link" id="logout">로그아웃</a>';
        const logoutButton = document.getElementById('logout');

        if (logoutButton) {
            logoutButton.addEventListener('click', event => {
                function success() {
                    // 로컬 스토리지에 저장된 액세스 토큰을 삭제
                    localStorage.removeItem('access_token');

                    // 쿠키에 저장된 리프레시 토큰을 삭제
                    deleteCookie('refresh_token');
                    location.replace('/about');
                }
                function fail() {
                    alert('로그아웃 실패했습니다.');
                }

                httpRequest('DELETE','/api/refresh-token', null, success, fail);
            });
        }
    } else {
        authLink.innerHTML = '<a class="nav-link" href="/login">로그인</a>';
    }
});