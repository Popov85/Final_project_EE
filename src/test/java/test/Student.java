package test;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Created by Andrey on 20.03.2017.
 */
@Entity
@Table(name = "student")
public class Student {
        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "ST_ID")
        private Long id;

        @Column(name = "ST_NAME")
        private String name;

        @OneToOne(fetch = FetchType.EAGER)
        @Cascade(org.hibernate.annotations.CascadeType.ALL)
        @JoinColumn(name="BOOK_ID", unique=true, nullable=false, updatable=false)
        private StudentBook studentBook;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public StudentBook getStudentBook() {
                return studentBook;
        }

        public void setStudentBook(StudentBook studentBook) {
                this.studentBook = studentBook;
        }
}
