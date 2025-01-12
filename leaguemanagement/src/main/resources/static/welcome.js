document.addEventListener('DOMContentLoaded', () => {
    const slider = document.querySelector('.slider');
    const slides = document.querySelector('.slides');
    const slideCount = document.querySelectorAll('.slide').length;
    let currentIndex = 0;
    let autoSlideInterval;

    function showSlide(index) {
        if (index >= slideCount) currentIndex = 0;
        else if (index < 0) currentIndex = slideCount - 1;
        else currentIndex = index;

        slides.style.transform = `translateX(-${currentIndex * 100}%)`;
    }

    function startAutoSlide() {
        // Clear any existing interval
        clearInterval(autoSlideInterval);

        // Set a new interval
        autoSlideInterval = setInterval(() => {
            showSlide(currentIndex + 1);
        }, 3000);
    }

    startAutoSlide();

    document.querySelector('.prev').addEventListener('click', () => {
        showSlide(currentIndex - 1);
        startAutoSlide();
    });
    
    document.querySelector('.next').addEventListener('click', () => {
        showSlide(currentIndex + 1);
        startAutoSlide();
    });

    document.addEventListener('keydown', (event) => {
        if (event.key === 'ArrowLeft') {
            showSlide(currentIndex - 1);
            startAutoSlide();
        } else if (event.key === 'ArrowRight') {
            showSlide(currentIndex + 1);
            startAutoSlide();
        }
    });
});