# PopUpWidget
Android PopUpFragment

![](https://github.com/app-z/PopUpWidget/blob/master/popup300.gif)

How to use


Create Fragment<br>
1500 - remove message interval in ms. Default 3000 if empty

```
    public void showFragment(View view) {

        Fragment newFragment = PopUpFragment.newInstance(1500);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, newFragment, PopUpFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
```

Add message to PopUp
```
    public void addMessage(View view) {
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null){
            String text = "Message " + new Random().nextGaussian();
            int icon = Math.abs(new Random().nextInt() % 3);
            popup.addMessage0ToPopUp(icon, text);
        }
    }

```

Remove message from PopUp
```
    public void removeMessage(View view) {
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null){
            popup.removeMessagePopUp();
        }
    }
```
