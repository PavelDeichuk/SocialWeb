package com.pavel.socialweb.Service;

import com.pavel.socialweb.Dto.SubscriptionDto;
public interface SubscriptionService {

    SubscriptionDto subscription(Long sub_user_id, Long pub_user_id);

    SubscriptionDto unSubscription(Long sub_user_id, Long pub_user_id);

}
