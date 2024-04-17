/**
 * 
 */

function changeImageClass(fromClass, toClass) {
    var image = document.getElementById('image');
    image.classList.remove(fromClass);
    image.classList.add(toClass);
}