/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.mailchimp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Усов Андрей
 * @since 04.10.2018
 */
@Service
public class MailChimpService {

    private static final Logger log = LoggerFactory.getLogger(MailChimpService.class);
    private static final String API_URL = "https://us20.api.mailchimp.com/3.0";
    private static final String LIST = API_URL + "/lists/%s";
    private static final String INTEREST_CATEGORIES = LIST + "/interest-categories";
    private static final String INTERESTS = INTEREST_CATEGORIES + "/%s/interests";
    private static final String MEMBERS = LIST + "/members";
    private static final String MEMBER = MEMBERS + "/%s";
/*
    public MailChimpMember getOrCreateMember(String email) {
        MailChimpMember mailChimpMember = getMember(MAIL_CHIMP_LIST_ID, email);
        if (mailChimpMember == null) {
            mailChimpMember = new MailChimpMember();
            mailChimpMember.setEmailAddress(email);
            mailChimpMember.setStatusIfNew("subscribed");
            mailChimpMember = updateMember(MAIL_CHIMP_LIST_ID, mailChimpMember);
        }
        return mailChimpMember;
    }

    public void addToGroup(MailChimpMember member, String groupName) {
        Interest interest = getInterest(MAIL_CHIMP_LIST_ID, groupName);
        if (Boolean.TRUE.equals(member.getInterests().get(interest.getId()))) {
            //Вначале нужно исключить эту группу, раз она уже есть
            saveInterest(member, interest, false);
        }
        saveInterest(member, interest, true);
    }

    private void saveInterest(MailChimpMember member, Interest interest, boolean result) {
        member.getInterests().put(interest.getId(), result);
        updateMember(MAIL_CHIMP_LIST_ID, member);
    }

    public MailChimpList getList(String listId) {
        String url = String.format(LIST, listId);
        HttpGet httpGet = auth(Http.get(url));
        return httpGet.responseInputStream(MailChimpList.class);
    }

    public Interest getInterest(String listId, String interestName) {
        InterestCategories interestCategories = getInterestCategories(listId);
        for (Category category : interestCategories.getCategories()) {
            Interests interests = getInterests(listId, category.getId());
            for (Interest interest : interests.getInterests()) {
                if (interest.getName().equalsIgnoreCase(interestName)) {
                    return interest;
                }
            }
        }
        throw new IllegalStateException("Error on find interest by name:" + interestName);
    }

    private Interests getInterests(String listId, String interestCategoryId) {
        String url = String.format(INTERESTS, listId, interestCategoryId);
        HttpGet httpGet = auth(Http.get(url));
        return httpGet.responseInputStream(Interests.class);
    }

    private InterestCategories getInterestCategories(String listId) {
        String url = String.format(INTEREST_CATEGORIES, listId);
        HttpGet httpGet = auth(Http.get(url));
        log.debug(httpGet.responseBody());
        return httpGet.responseInputStream(InterestCategories.class);
    }

    public MailChimpMember getMember(String listId, String email) {
        String url = String.format(MEMBER, listId, DigestUtils.md5Hex(email.toLowerCase()));
        HttpGet httpGet = auth(Http.get(url));
        log.debug(httpGet.responseBody());
        return httpGet.responseInputStream404(MailChimpMember.class);
    }

    public MailChimpMember updateMember(String listId, MailChimpMember member) {
        String url = String.format(MEMBER, listId, DigestUtils.md5Hex(member.getEmailAddress().toLowerCase()));
        HttpPut post = auth(new HttpPut(url));
        post.requestBodyJson(member);
        return post.responseInputStream404(MailChimpMember.class);
    }

    private <T extends HttpRequestBase, R extends Http<T>> R auth(R http) {
        String auth = "asdasdasd" + ":" + apiKey;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        http.getRequest().setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        return http;
    }*/
}
