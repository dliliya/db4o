package javaapplication2;
import com.db4o.*;
import java.util.ArrayList;
import java.util.List;
import com.db4o.query.Constraint;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import static java.lang.System.in;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JavaApplication2 {

    public static void main(String[] args) {
        // accessDb4o
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded
        .newConfiguration(), "D:\\mydb");
        try {
            fill_base(db);            
            printActorDirectorPerson(db);  // Функция, которая выводит в консоль список людей, которые одновременно и актеры и режиссеры*/
            ListActerNotDigital(db);       // Функция, которая выводит в консоль список актеров, которые снимались в фильмах цифрового формата */ 
            ListFilmActorOskar(db);        // Функция, которая выводит в консоль список фильмов, в которых снимался актер, имеющий на тот момент оскар   
            clearDatabase(db);           
        } finally {
            db.close();
        }
    }
    
    
    
    // Функция выводит в консоль список людей, которые одновременно и актеры и режиссеры
    public static void printActorDirectorPerson(ObjectContainer db){
        // Сделаем запрос актеров и режиссеров и поместим их в список
        Query queryActor = db.query();
        queryActor.constrain(Actor.class);
        
        Query queryDirector = db.query();
        queryDirector.constrain(Director.class);
        
        List<Actor> resultActor = queryActor.execute();
        List<Director> resultDirector = queryDirector.execute();
        
        
        List<Person> people = new ArrayList(); // Список для людей являющихся одновременно режиссером и актером
        
        // Вложенным циклом сравниваем суперклассы каждого актера и режиссера  
        resultActor.forEach((actor) -> 
        {
            Person actorPerson = actor.getPerson();
            resultDirector.forEach((director) -> 
            {
                Person directorPerson = director.getPerson();
                // Если суперклассы равны, то добавляем человека в целевой список и выводим в консоль
                if (actorPerson.equals(directorPerson)) 
                {
                    people.add(actorPerson);
                    System.out.println(directorPerson.toString());
                }
            });
        });
       
    }
   
    // Функция выводит в консоль список актеров, которые снимались в фильмах цифрового формата 
    // Сначала шагом идет выборка всех режиссеров, которые снимают в цифровом формате, далее в их фильмах выбираются различные актеров
    public static void ListActerNotDigital(ObjectContainer db)
    {
        // Запрос режиссеров, снимающих в цифровом формате: formatDigital == true
        Query queryDirector = db.query();
        queryDirector.constrain(Director.class);
        Query formatDigitalQuery = queryDirector.descend("formatDigital");
        formatDigitalQuery.constrain(true);
        
        List<Director> resultDirector = queryDirector.execute();
        Map<String, Actor> hashMap = new HashMap<>();
        
        resultDirector.forEach((director) -> 
        {
            // Запрашиваем список фильмов режиссера...
            List<Film> films = director.getfilms();
            // ... и проходимся по ним в цикле
            films.forEach((film)->
            {
                // Получаем список актеров в фильме
                List<Actor> listActors= film.getActors();
                // Для хранения актеров используется контейнер Мар (что позволяет избежать дублей) 
                // где в качестве ключа используется возвращаемое значение функции класса toString(),
                // а значение - объект актера
                listActors.forEach((actor)->
                {
                    // Если актер отсутствует - он добавляется в контейнер
                    if (hashMap.get(actor.toString())==null)
                        hashMap.put(actor.toString(), actor);
                });
                
            });
        });
        
        Set<Map.Entry<String, Actor>> set = hashMap.entrySet();

        // Выведем в консоль получившийся набор
        for (Map.Entry<String, Actor> me : set) 
        {
            System.out.println(me.getValue());
        }
        
     }
    
    // Функция выводит в консоль список фильмов, в которых снимался актер, имеющий на тот момент оскар
    public static void ListFilmActorOskar(ObjectContainer db)
    {
        // Для хранения целевого списка фильмов используется контейнер Мар (что позволяет избежать дублей) 
        // где в качестве ключа используется возвращаемое значение функции класса toString(),
        // а значение - объект фильма
        Map<String, Film> hashMap = new HashMap<>();
        List<Actor> results = db.query(new Predicate<Actor>() {
            public boolean match(Actor actor) {                
                // Выборка актеров имеющих оскар
                if (actor.isHas_oskar())
                {
                    // Выборка фальмов в которых актер снимался
                    List<Film> films = actor.getFilms();
                    for (Film film : films) {
                        // Сравниваем год получения оскара и год создания фильма
                        // Если год получения оскара актером меньше чем год выпуска фильма - фильм добавляется в контейнер
                        if (film.getYear()>actor.getYear_oskar()) {
                            if (hashMap.get(film.toString())==null)
                                hashMap.put(film.toString(), film);
                            return true;
                        }
                    }
                    
                }
                return false;
            }
        });
        
        Set<Map.Entry<String, Film>> set = hashMap.entrySet();

        // Выведем в консоль получившийся набор
        for (Map.Entry<String, Film> me : set) 
        {
            System.out.println(me.getValue());
        }
    }
    
    public static void listResult(List<?> result){
    	//System.out.println(result.size());
        result.forEach((o) -> {
            System.out.println(o);
        });
    }
    
    public static void fill_base(ObjectContainer db){   
            //director
            Director director1=new Director("Quentin Tarantino","M", "USA",54,0,false);
            db.store(director1);
            
            Director director2=new Director("Guy Ritchie","M", "UK",48,5, true);
            db.store(director2);
            
            Director director3=new Director("Leonardo DiCaprio","M", "USA",42,0,true);
            db.store(director3);
            
            Director director4=new Director("Joss Whedon","M", "UK",52,2, true);
            db.store(director4);
            
            Director director5=new Director("Martin Scorsese","M", "UK",74,2,false);
            db.store(director5);
            
            //films
            Film film1=new Film("The Avengers",director4, "USA",2012);
            db.store(film1);
            Film film2=new Film("Sherlock Holmes",director3, "USA",2009);
            db.store(film2);
            Film film3=new Film("Snatch",director2, "USA",2003);
            db.store(film3);
            Film film4=new Film("Lock, Stock and Two Smoking Barrels",director2, "UK",1998);
            db.store(film4);
            Film film5=new Film("Shutter Island",director5, "USA",2009);
            db.store(film5);
            
            //set films derictor
            ArrayList<Film> filmsD1 = new ArrayList<>();
            filmsD1.add(film1);
            director4.setFilms(filmsD1);
            db.store(director4);
            
            ArrayList<Film> filmsD2 = new ArrayList<>();
            filmsD2.add(film2);
            director3.setFilms(filmsD2);
            db.store(director3);
            
            ArrayList<Film> filmsD3 = new ArrayList<>();
            filmsD3.add(film3);
            filmsD3.add(film4);
            director2.setFilms(filmsD3);
            db.store(director2);
            
            ArrayList<Film> filmsD4 = new ArrayList<>();
            filmsD4.add(film5);
            director5.setFilms(filmsD4);
            db.store(director5);
            
            ArrayList<Film> films1 = new ArrayList<>();
            films1.add(film1);
            films1.add(film2);
            
            ArrayList<Film> films2 = new ArrayList<>();
            films2.add(film3);
            
            ArrayList<Film> films3 = new ArrayList<>();
            films3.add(film3);
            films3.add(film4);
            
            ArrayList<Film> films4 = new ArrayList<>();
            films4.add(film5);
            films4.add(film2);
            films4.add(film3);
            
            //actors
            Actor actor1=new Actor(films1, "Robert Downey Jr.", "M", "USA", 52,2,79,false);
            db.store(actor1);
            Actor actor2=new Actor(films2, "Brad Pitt", "M", "USA", 53,1,66,true,2002);
            db.store(actor2);
            Actor actor3=new Actor(films3, "Jason Statham", "M", "UK", 49,3,38,false);
            db.store(actor3);
            Actor actor4=new Actor(films4, "Leonardo DiCaprio", "M", "USA", 42,0,37,true,2017);
            db.store(actor4);
            
            ArrayList<Actor> actors1 = new ArrayList<>(); 
            actors1.add(actor1);
            film1.setActors(actors1);
            db.store(film1);
            
            ArrayList<Actor> actors2 = new ArrayList<>();
            actors2.add(actor1);
            actors2.add(actor4);
            film2.setActors(actors2);
            db.store(film2); 
            
            ArrayList<Actor> actors3= new ArrayList<>();
            actors3.add(actor2); 
            actors3.add(actor3);
            actors3.add(actor1);
            actors3.add(actor4); 
            film3.setActors(actors3);
            db.store(film3);
            
            ArrayList<Actor> actors4= new ArrayList<>();
            actors4.add(actor3); 
            film4.setActors(actors4);
            db.store(film4);
            
            ArrayList<Actor> actors5= new ArrayList<>();
            actors5.add(actor4); 
            actors5.add(actor2);
            film5.setActors(actors5);
            db.store(film5);            
    }

    public static void clearDatabase(ObjectContainer db) {
        ObjectSet result=db.queryByExample(Film.class);
        while(result.hasNext()) {
            db.delete(result.next());
        }
        ObjectSet result1=db.queryByExample(Actor.class);
        while(result1.hasNext()) {
            db.delete(result1.next());
        }
        ObjectSet result2=db.queryByExample(Person.class);
        while(result2.hasNext()) {
            db.delete(result2.next());
        }
        ObjectSet result3=db.queryByExample(Director.class);
        while(result3.hasNext()) {
            db.delete(result3.next());
        }       
    }
    

    
}
