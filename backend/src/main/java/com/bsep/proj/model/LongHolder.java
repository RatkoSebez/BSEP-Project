package com.bsep.proj.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// posto ne znam kako da cuvam List<Long>, morao sam napraviti ovu klasu
public class LongHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long holdenId;

    public LongHolder(Long holdenId) {
        this.holdenId = holdenId;
    }
}
