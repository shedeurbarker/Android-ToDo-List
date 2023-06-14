# Android To DO List App
A simple To-Do List tracker with basic functionality

# Functions
- Keeps track of a to do list
- Create, Edit and delete list items
- Long Click to Delete
- I added a counter below the title to track total number of items in the list
- Three method, getItems, updateList and deleteItem are use to fetch and update the array list respectively
- A second Activity is use to add a new item
- The onResume method of the main activity helps to update the list dynamically if it was added on the AddTask Activity

# Database
- I used SharedPreferences to store an array list as a set. 

# Future Functionality
1. Change long click to delete into swipe to delete
2. Add Categories using color bars at the left of each list
3. Use custom listView layout with RecyclerView to test view binding functions

# Known Bugs
- No known bugs yet