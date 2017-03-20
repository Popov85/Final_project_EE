package test;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Andrey on 20.03.2017.
 */
@Entity
@Table(name = "student_book")
public class StudentBook {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "BOOK_ID")
        private Long id;

        @Column(name = "BOOK_NUMBER")
        private String number;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getNumber() {
                return number;
        }

        public void setNumber(String number) {
                this.number = number;
        }
}
