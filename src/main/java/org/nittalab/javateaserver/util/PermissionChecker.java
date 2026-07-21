package org.nittalab.javateaserver.util;

import org.nittalab.javateaserver.models.User;
import org.nittalab.javateaserver.repositories.UserRepository;

public class PermissionChecker {

    private PermissionChecker() {
    }

    /**
     * 権限があるかどうかを判定する
     * permission: "だれでも" / "同じ大学" / "同じ学部"
     *
     * view-permission(質問の閲覧権限)、res-permission(質問への回答権限)の
     * どちらの判定にも使える共通ロジック。
     */
    public static boolean hasPermission(String permission, String ownerUid, String requesterUid,
                                        UserRepository userRepository) {

        // 質問の投稿者本人は常に許可
        if (ownerUid != null && ownerUid.equals(requesterUid)) {
            return true;
        }

        if ("だれでも".equals(permission)) {
            return true;
        }

        User owner = userRepository.getUser(ownerUid);
        User requester = userRepository.getUser(requesterUid);

        // どちらかのユーザーが存在しない場合は判定できないため拒否
        if (owner == null || requester == null) {
            return true;
        }

        if ("同じ大学".equals(permission)) {
            return owner.getUniversity() != null
                    && owner.getUniversity().equals(requester.getUniversity());
        }

        if ("同じ学部".equals(permission)) {
            return owner.getFaculty() != null
                    && owner.getFaculty().equals(requester.getFaculty());
        }

        // 未知の値は安全側に倒して拒否
        return false;
    }
}