package user;

import ex6.LegoSetDao;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.util.Optional;


public class Main {

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User user = User.builder()
                    .username("007")
                    .password("asd")
                    .name("James Bond")
                    .email("007@gmail.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            dao.insert(user);

            User danny = User.builder()
                    .username("Dan")
                    .password("password")
                    .name("Danny DeVito")
                    .email("bigman@gmail.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1947-11-17"))
                    .enabled(true)
                    .build();


            dao.insert(danny);

            dao.list().stream().forEach(System.out::println);



            //Finding by ID
            Optional<User> optionalUser= dao.findById(1);
            User user2 =optionalUser.get();
            System.out.println(user2.getName());

            //Finding by Username
            optionalUser=dao.findByUsername("Dan");
            user2 =optionalUser.get();
            System.out.println(user2.getName());



            System.out.println("Deleting..");
            dao.delete(user);
            if (dao.count()>0) {
                dao.list().stream().forEach(System.out::println);
            }else{
                System.out.println("The database is empty");
            }


        }
    }




}
