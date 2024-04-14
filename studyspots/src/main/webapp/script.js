/**
 * 
 */

// Function to change image
function changeImage(event) {
    // Get the value of the selected radio button
    const selectedValue = event.target.value;

    // Update image source based on selected value
    if (selectedValue === 'blank-tommy.svg') {
        blank-tommy.src = 'filled-tommy.svg'; // Change 'image1.jpg' to the path of your first image
    } 
}

// Attach change event listener to each radio button
radioButtons.forEach(radioButton => {
    radioButton.addEventListener('change', changeImage);
});