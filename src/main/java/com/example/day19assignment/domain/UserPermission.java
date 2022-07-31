package com.example.day19assignment.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="permission_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "permissionId_id")
    private Permission permissionId;

    @Override
    public String toString() {
        return "UserPermission{" +
                "id=" + id +
                "permissions=" + permissionId.toString() +
                '}';
    }
}
