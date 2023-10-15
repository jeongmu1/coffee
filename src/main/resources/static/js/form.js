document.getElementById('addItem').addEventListener('click', function() {
  const itemsDiv = document.getElementById('items');
  const newItem = document.querySelector('.item').cloneNode(true);
  const index = itemsDiv.children.length;

  newItem.querySelectorAll('select, input').forEach(input => {
    input.name = input.name.replace(/\[\d+]/, '[' + index + ']');
    input.id = input.name;
    input.value = '';
  });

  newItem.querySelectorAll('label').forEach(label => {
    label.setAttribute('for', label.htmlFor.replace(/\d+/, index));
  });

  itemsDiv.appendChild(newItem);
});
