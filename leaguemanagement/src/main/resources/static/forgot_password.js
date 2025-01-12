let box = document.querySelector('.wrapper');

setTimeout(function() {
  box.style.animation = 'appear 2s ease-in-out';
  setTimeout(function() {
    box.style.opacity = '1';
  }, 1900);
}, 500);