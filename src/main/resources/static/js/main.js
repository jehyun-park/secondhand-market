const tabItems = document.querySelectorAll('.tab-item');
const tabContentItems = document.querySelectorAll('.tab-content');

function selectItem(e) {
  removeActive();
  removeShow();
  this.classList.add('active');
  const tabContentItem = document.querySelector(`#${this.dataset.tab}`);
  tabContentItem.classList.add('active');
}

function removeActive() {
  tabItems.forEach(item => item.classList.remove('active'));
}

function removeShow() {
  tabContentItems.forEach(item => item.classList.remove('active'));
}

tabItems.forEach(item => item.addEventListener('click', selectItem));
