# bookinggkz

#hosted in https://bookinggkz.herokuapp.com

! ADDING IMAGE TO TOPIC and EVENT does not work on heroku , but works locally cool! 
! WebSite works COOL !
! You can test,if you want !
! just clone the repository !

"The reason that you can't store files locally on Heroku is that this just isn't 
how Heroku works. Heroku takes a copy of your git repository and bundles it up into
a "slug" which then gets run on their servers. Anything outside your slug 
(i.e. that's not stored within your git repo) will be lost when the dyno (virtual UNIX instance) restarts."

#scenario:
 roles:admin,organizators,users;

1.As an admin:
  *can see all informations about users,organizators,events
  *can to add organizators(edit,remove)
  *can to add topic(edit,remove)
  *can to add event(edit,remove)
  
2.As a moderator(organization)
  *can see info about his events
  *can to add event
  *can to book user
  
3.As a user(client){
  *can see events by favourite topic
  *can book event
  *can cancel event while organization not books
}

#admin:    nurik@gmail.com - qwerty
          
#organizations:  holaalm@gmail.com - holahola1581 ,
                 almatyorg@gmail.com - almatyorg1474 ,
                 jummy@gmail.com - jummy5589 ,
                 disneykz@mail.ru - disney8899 ,
                 groovy@mail.ru - groovy5548 ,
                 lalaland@mail.ru - lalaland4561 ,
                 citybar@gmail.com - citybar3696 ,
                 travenA@mail.ru - travena5589 ,
                 sahalina@mail.ru - sahalina3681
          
          
 
