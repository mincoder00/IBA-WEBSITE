function enableDragScroll(containerSelector) {
    const container = document.querySelector(containerSelector);

    let isDown = false;
    let startX;
    let scrollLeft;

    container.addEventListener('mousedown', (e) => {
        isDown = true;
        container.classList.add('active');
        startX = e.pageX - container.offsetLeft;
        scrollLeft = container.scrollLeft;
        container.style.cursor = 'grabbing'; // 마우스 커서를 드래그 중인 상태로 변경
    });

    container.addEventListener('mouseleave', () => {
        isDown = false;
        container.classList.remove('active');
        container.style.cursor = 'grab'; // 드래그가 종료되면 다시 grab 상태로 변경
    });

    container.addEventListener('mouseup', () => {
        isDown = false;
        container.classList.remove('active');
        container.style.cursor = 'grab'; // 드래그가 종료되면 다시 grab 상태로 변경
    });

    container.addEventListener('mousemove', (e) => {
        if (!isDown) return;
        e.preventDefault();
        const x = e.pageX - container.offsetLeft;
        const walk = (x - startX) * 1; // 움직이는 속도를 조정하려면 이 값을 변경할 수 있습니다.
        container.scrollLeft = scrollLeft - walk;
    });
}

// 'awards-container'와 'activity-container'에 드래그 스크롤 기능을 적용
enableDragScroll('.awards-container');
enableDragScroll('.activity-container');
