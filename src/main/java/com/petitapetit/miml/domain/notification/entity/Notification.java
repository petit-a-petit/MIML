package com.petitapetit.miml.domain.notification.entity;

import com.petitapetit.miml.domain.notification.TempUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "notification_type")
@EntityListeners(AuditingEntityListener.class)
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    @CreatedDate
    private LocalDateTime createdAt;

    public Notification(String requestedUserEmail){
        this.userEmail = requestedUserEmail;
    }

    // 메일에 작성할 내용을 반환하는 함수를 구현하도록 강제함.
    /**
     * @return 메일 제목 반환
     */
    public abstract String makeSubject();

    /**
     * @return 메일 내용 반환
     */
    public abstract String makeText();
}
