# Pre-work - *ListDemo*

**ListDemo** is an android app that allows building a todo list and basic todo items management functionality 
including adding new items, editing and deleting an existing item.

Submitted by **Priyanka Eratkar**

Time Spent: **20** hours

## User Stories

The following **required** functionality is completed:

* User can **successfully add and remove items** from the todo list
* User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* User can **persist todo items** and retrieve them properly on app restart

The following **optional** functionality is completed:
* Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* Improve style of the todo items in the list [using a custom adapter] *Cursor Adpater* (http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView) 
* Add support for completion due dates for todo items (and display within listview item)
* Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity to confirm item deletion
* Add support for selecting the priority of each todo item 
* Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:
* Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) for getting user confirmation to delete an item from todo list
* Model classes to access data 
* Spinner for priority select
* Datepicker
* Reusable Activity to Add and Delete todo item

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/XpZtsXV.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes
It was exciting to build app on android. I got familiar with different approaches one can use to 
implement the feature. However, pushing project to Github was new to me. I made use of command line
tools, yet kept on getting error "Your repository already exists", although I had explicitly deleted 
that directory. One of friends suggested me to go through VCS->Import into Version Control to share 
project on github. This saved my time further. I will keep on updating app to enhance it further.


## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
