/*
 * AdministratorRepository.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

import java.util.Collection;
import java.util.Map;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

    /*	1. The average, the minimum, the maximum, and the standard deviation of the
			number of showrooms per user. */
    @Query(value = "select avg(usuario.showrooms) " +
            "from (select sum(case when s.user_id = u.id then 1 else 0 end) showrooms " +
            "from Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Double findAverageShowroomsPerUser();

    @Query(value = "select max(usuario.showrooms) " +
            "from (select sum(case when s.user_id = u.id then 1 else 0 end) showrooms " +
            "from Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Integer findMaximunShowroomsPerUser();

    @Query(value = "select min(usuario.showrooms) " +
            "from (select sum(case when s.user_id = u.id then 1 else 0 end) showrooms " +
            "from Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Integer findMinimunShowroomsPerUser();

    @Query(value = "select sqrt(sum(usuario.showrooms*usuario.showrooms)/count(usuario.showrooms) - avg(usuario.showrooms)*avg(usuario.showrooms)) " +
            "from (select sum(case when s.user_id = u.id then 1 else 0 end) showrooms " +
            "from Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Double findStdevShowroomsPerUser();

    @Query("select s.user, count(s.user) from Showroom s group by s.user")
    Map<User, Integer> findSCountShowroomsPerUser();

    /*	2. The average, the minimum, the maximum, and the standard deviation of the
			number of items per user. */
    @Query(value = "select avg(usuario.items) " +
                    "from (select sum(case when i.showroom_id = s.id and s.user_id= u.id then 1 else 0 end) items " +
            "from Item i join Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Double findAverageItemsPerUser();

    @Query(value = "select max(usuario.items) " +
            "from (select sum(case when i.showroom_id = s.id and s.user_id= u.id then 1 else 0 end) items " +
            "from Item i join Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Integer findMaximunItemsPerUser();

    @Query(value = "select min(usuario.items) " +
            "from (select sum(case when i.showroom_id = s.id and s.user_id= u.id then 1 else 0 end) items " +
            "from Item i join Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Integer findMinimunItemsPerUser();

    @Query(value = "select sqrt(sum(usuario.items*usuario.items)/count(usuario.items) - avg(usuario.items)*avg(usuario.items)) " +
            "from (select sum(case when i.showroom_id = s.id and s.user_id= u.id then 1 else 0 end) items " +
            "from Item i join Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Double findStdevItemsPerUser();

    @Query("select i.showroom.user, count(i.showroom.user) from Item i group by i.showroom.user")
    Map<User, Integer> findSCountItemsPerUser();

    /*	3. The average, the minimum, the maximum, and the standard deviation of the
			number of requests per user. */
    @Query(value = "select avg(usuario.requests) " +
            "from (select sum(case when r.user_id = u.id then 1 else 0 end) requests " +
            "from Request r join User u group by u.id) as usuario", nativeQuery = true)
    Double findAverageRequestsPerUser();

    @Query(value = "select max(usuario.requests) " +
            "from (select sum(case when r.user_id = u.id then 1 else 0 end) requests " +
            "from Request r join User u group by u.id) as usuario", nativeQuery = true)
    Integer findMaximunRequestsPerUser();

    @Query(value = "select min(usuario.requests) " +
            "from (select sum(case when r.user_id = u.id then 1 else 0 end) requests " +
            "from Request r join User u group by u.id) as usuario", nativeQuery = true)
    Integer findMinimunRequestsPerUser();

    @Query(value = "select sqrt(sum(usuario.requests*usuario.requests)/count(usuario.requests) - avg(usuario.requests)*avg(usuario.requests)) " +
            "from (select sum(case when r.user_id = u.id then 1 else 0 end) requests " +
            "from Request r join User u group by u.id) as usuario", nativeQuery = true)
    Double findStdevRequestsPerUser();

    @Query("select r.user, count(r.user) from Request r group by r.user")
    Map<User, Integer> findSCountRequestsPerUser();


    /*	4. The average, the minimum, the maximum, and the standard deviation of the
			number of rejected Requests per user. */
    @Query(value = "select avg(usuario.requests) " +
            "from (select sum(case when r.user_id = u.id then 1 else 0 end) requests " +
            "from Request r join User u where r.status='REJECTED' group by u.id) as usuario", nativeQuery = true)
    Double findAverageRejectedRequestsPerUser();

    @Query(value = "select max(usuario.requests) " +
            "from (select sum(case when r.user_id = u.id then 1 else 0 end) requests " +
            "from Request r join User u where r.status='REJECTED' group by u.id) as usuario", nativeQuery = true)
    Integer findMaximunRejectedRequestsPerUser();

    @Query(value = "select min(usuario.requests) " +
            "from (select sum(case when r.user_id = u.id then 1 else 0 end) requests " +
            "from Request r join User u where r.status='REJECTED' group by u.id) as usuario", nativeQuery = true)
    Integer findMinimunRejectedRequestsPerUser();

    @Query(value = "select sqrt(sum(usuario.rejectedRequests*usuario.rejectedRequests)/count(usuario.rejectedRequests) - avg(usuario.rejectedRequests)*avg(usuario.rejectedRequests)) " +
            "from (select sum(case when r.user_id = u.id then 1 else 0 end) rejectedRequests " +
            "from Request r join User u where r.status='REJECTED' group by u.id) as usuario", nativeQuery = true)
    Double findStdevRejectedRequestsPerUser();

    @Query("select r.user, count(r.user) from Request r where r.status='REJECTED' group by r.user")
    Map<User, Integer> findSCountRejectedRequestsPerUser();


    /* DashBoard B */
    /*	1. The average, the minimum, the maximum, and the standard deviation of the
           number of chirps per actor. */
    @Query(value = "select avg(usuario.chirps) " +
            "from (select sum(case when c.actor_id = u.id then 1 else 0 end) chirps " +
            "from Chirp c right join User u on c.actor_id=u.id left join Administrator a on c.actor_id=a.id " +
            "left join Agent ag on c.actor_id=ag.id group by u.id) as usuario", nativeQuery = true)
    Double findAverageChirpsPerUser();

    @Query(value = "select max(usuario.chirps) " +
            "from (select sum(case when c.actor_id = u.id then 1 else 0 end) chirps " +
            "from Chirp c right join User u on c.actor_id=u.id left join Administrator a on c.actor_id=a.id " +
            "left join Agent ag on c.actor_id=ag.id group by u.id) as usuario", nativeQuery = true)
    Integer findMaximunChirpsPerUser();

    @Query(value = "select min(usuario.chirps) " +
            "from (select sum(case when c.actor_id = u.id then 1 else 0 end) chirps " +
            "from Chirp c right join User u on c.actor_id=u.id left join Administrator a on c.actor_id=a.id " +
            "left join Agent ag on c.actor_id=ag.id group by u.id) as usuario", nativeQuery = true)
    Integer findMinimunChirpsPerUser();

    @Query(value = "select sqrt(sum(usuario.chirps*usuario.chirps)/count(usuario.chirps) - avg(usuario.chirps)*avg(usuario.chirps)) " +
            "from (select sum(case when c.actor_id = u.id then 1 else 0 end) chirps " +
            "from Chirp c right join User u on c.actor_id=u.id left join Administrator a on c.actor_id=a.id " +
            "left join Agent ag on c.actor_id=ag.id group by u.id) as usuario", nativeQuery = true)
    Double findStdevChirpsPerUser();

    @Query("select c.actor, count(c.actor) from Chirp c group by c.actor")
    Map<User, Integer> findSCountChirpsPerUser();

    /*	2. The average, the minimum, the maximum, and the standard deviation of the
           number of followers per actor. */
    @Query(value = "select avg(usuario.followers) " +
            "from (select sum(case when s.user_id = u.id then 1 else 0 end) followers " +
            "from Subscription s join User u group by u.id) as usuario", nativeQuery = true)
    Double findAverageFollowersPerUser();

    @Query(value = "select max(usuario.followers) " +
            "from (select sum(case when s.user_id = u.id then 1 else 0 end) showrooms " +
            "from Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Integer findMaximunFollowersPerUser();

    @Query(value = "select min(usuario.followers) " +
            "from (select sum(case when s.user_id = u.id then 1 else 0 end) showrooms " +
            "from Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Integer findMinimunFollowersPerUser();

    @Query(value = "select sqrt(sum(usuario.followers*usuario.followers)/count(usuario.followers) - avg(usuario.followers)*avg(usuario.followers)) " +
            "from (select sum(case when s.user_id = u.id then 1 else 0 end) showrooms " +
            "from Showroom s join User u group by u.id) as usuario", nativeQuery = true)
    Double findStdevFollowersPerUser();

    @Query("select f.subscribedActor, count(f.subscriber) from Subscription f group by f.subscribedActor")
    Map<User, Integer> findSCountFollowersPerUser();

    /*	3. The average, the minimum, the maximum, and the standard deviation of the
           number of followeds per actor. */
    @Query(value = "select avg(usuario.followeds) " +
            "from (select count(s.subscriber_id) followeds from Subscription s group by s.subscribedActor_id) " +
            "as usuario ", nativeQuery = true)
    Double findAverageFollowedsPerUser();

    @Query(value = "select max(usuario.followeds) " +
            "from (select count(s.subscriber_id) followeds from Subscription s group by s.subscribedActor_id) " +
            "as usuario ", nativeQuery = true)
    Integer findMaximunFollowedsPerUser();

    @Query(value = "select min(usuario.followeds) " +
            "from (select count(s.subscriber_id) followeds from Subscription s group by s.subscribedActor_id) " +
            "as usuario ", nativeQuery = true)
    Integer findMinimunFollowedsPerUser();

    @Query(value = "select sqrt(sum(usuario.followeds*usuario.followeds)/count(usuario.followeds) - avg(usuario.followeds)*avg(usuario.followeds)) " +
            "from (select count(s.subscriber_id) followeds from Subscription s group by s.subscribedActor_id) " +
            "as usuario ", nativeQuery = true)
    Double findStdevFollowedsPerUser();

    @Query("select f.subscribedActor, count(f.subscriber) from Subscription f group by f.subscribedActor")
    Map<User, Integer> findSCountFollowedsPerUser();

    @Query("select c.topic, count(c.id) from Chirp c group by c.topic")
    Collection<Object> findChirpsNumberPerTopic();

    @Query(value = "select avg(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Double findAverageCommentsPerUser();

    @Query(value = "select max(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Integer findMaximunCommentsPerUser();

    @Query(value = "select min(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Integer findMinimunCommentsPerUser();

    @Query(value = "select sqrt(sum(usuario.comments*usuario.comments)/count(usuario.comments) - avg(usuario.comments)*avg(usuario.comments)) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Double findStdevCommentsPerUser();

    @Query("select c.actor, count(c.id) from Comment c group by c.actor")
    Map<User, Integer> findSCountCommentsPerUser();

    @Query(value = "select avg(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Double findAverageCommentsPerShowroom();

    @Query(value = "select max(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Integer findMaximunCommentsPerShowroom();

    @Query(value = "select min(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Integer findMinimunCommentsPerShowroom();

    @Query(value = "select sqrt(sum(usuario.comments*usuario.comments)/count(usuario.comments) - avg(usuario.comments)*avg(usuario.comments)) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Double findStdevCommentsPerShowroom();

    @Query("select c.actor, count(c.id) from Comment c group by c.actor")
    Map<User, Integer> findSCountCommentsPerShowroom();

    @Query(value = "select avg(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Double findAverageCommentsPerItem();

    @Query(value = "select max(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Integer findMaximunCommentsPerItem();

    @Query(value = "select min(usuario.comments) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Integer findMinimunCommentsPerItem();

    @Query(value = "select sqrt(sum(usuario.comments*usuario.comments)/count(usuario.comments) - avg(usuario.comments)*avg(usuario.comments)) " +
            "from (select count(c.id) comments from Comment c group by c.actor_id) " +
            "as usuario ", nativeQuery = true)
    Double findStdevCommentsPerItem();

    @Query("select c.actor, count(c.id) from Comment c group by c.actor")
    Map<User, Integer> findSCountCommentsPerItem();




}
   

   /* @Query("select t.administrative.id, t.administrative.userAccount.username, count(t.id) from Tender t group by t.administrative.id, t.administrative.name")
    Collection<Object> numberTenderByUser();

    @Query("select sum(case when t.interest = 'UNDEFINED' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'HIGH' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'MEDIUM' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'LOW' then 1 else 0 end)*100/count(t) from Tender t")
    Collection<Object> tendersByInterestLevel();

    @Query("select sum(case when o.state='CREATED' then 1 else 0 end),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end),  sum(case when o.state='ABANDONED' then 1 else 0 end), sum(case when o.state='PRESENTED' then 1 else 0 end), sum(case when o.state='WINNED' then 1 else 0 end), sum(case when o.state='LOSSED' then 1 else 0 end),  sum(case when o.state='CHALLENGED' then 1 else 0 end),  sum(case when o.state='DENIED' then 1 else 0 end) from Offer o")
    Collection<Object> offersByState();

    @Query("select o.commercial.name, sum(case when o.state='CREATED' then 1 else 0 end),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end),  sum(case when o.state='ABANDONED' then 1 else 0 end), sum(case when o.state='PRESENTED' then 1 else 0 end), sum(case when o.state='WINNED' then 1 else 0 end), sum(case when o.state='LOSSED' then 1 else 0 end),  sum(case when o.state='CHALLENGED' then 1 else 0 end),  sum(case when o.state='DENIED' then 1 else 0 end) from Offer o group by o.commercial.name")
    Collection<Object> offersByStateAndCommercial();

    @Query("select sum(case when o.state='CREATED' then 1 else 0 end)*100/count(o),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end)*100/count(o),  sum(case when o.state='ABANDONED' then 1 else 0 end)*100/count(o), sum(case when o.state='PRESENTED' then 1 else 0 end)*100/count(o), sum(case when o.state='WINNED' then 1 else 0 end)*100/count(o), sum(case when o.state='LOSSED' then 1 else 0 end)*100/count(o),  sum(case when o.state='CHALLENGED' then 1 else 0 end)*100/count(o),  sum(case when o.state='DENIED' then 1 else 0 end)*100/count(o)  from Offer o")
    Collection<Object> offersByStateRatio();

    @Query("select o.commercial.name, sum(case when o.state='CREATED' then 1 else 0 end)*100/count(o),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end)*100/count(o),  sum(case when o.state='ABANDONED' then 1 else 0 end)*100/count(o), sum(case when o.state='PRESENTED' then 1 else 0 end)*100/count(o), sum(case when o.state='WINNED' then 1 else 0 end)*100/count(o), sum(case when o.state='LOSSED' then 1 else 0 end)*100/count(o),  sum(case when o.state='CHALLENGED' then 1 else 0 end)*100/count(o),  sum(case when o.state='DENIED' then 1 else 0 end)*100/count(o) from Offer o group by o.commercial.name")
    Collection<Object> offersByStateAndCommercialRatio();

    @Query("select o "
            + "from Offer o "
            + "where year(o.presentationDate) = year(?1) "
            + "and month(o.presentationDate) = month(?1) "
            + "order by o.amount desc")
    List<Offer> findTopOffersOnMonth(Date date, Pageable pageSize);

    @Query("select o from Offer o "
            + "where o.presentationDate between ?1 and ?2 "
            + "and o.state = 'WINNED' "
            + "order by o.amount desc")
    List<Offer> findTopWinedOffersOnThreeMonths(Date from, Date to, Pageable pageSize);

    @Query("select cr.name, avg(cr.economicalOffer / t.estimatedAmount) from CompanyResult cr join cr.tenderResult tr join tr.tender t where t.estimatedAmount != 0 group by cr.name")
    Collection<Object> findAvgRatioAmounOfferOverTender();

    @Query("select cr.name, count(cr.name) from CompanyResult cr group by cr.name order by count(cr.name) desc")
    List<Object> findTopFrecuentsCompanies(Pageable pageSize);

    @Query("select cr.name, count(cr.name) from CompanyResult cr "
            + "where cr.state = 'WINNER' "
            + "group by cr.name "
            + "order by count(cr.name) desc")
    List<Object> findTopFrecuentsWinnersCompanies(Pageable pageSize);

    @Query("select t from Tender t "
            + "where t.maxPresentationDate between ?1 and ?2 "
            + "and t.interest='HIGH' "
            + "and not exists (select o from Offer o where t=o.tender) "
            + "order by t.estimatedAmount desc")
    List<Tender> findHighInterestNoOferTendersCloseToExpire(Date from, Date to);

    @Query("select t from Tender t "
            + "where t.maxPresentationDate between ?1 and ?2 "
            + "and exists (select o from Offer o where t=o.tender and o.state='ABANDONED') "
            + "order by t.estimatedAmount desc")
    List<Tender> findHighInterestTendersWithAbandonedOfferCloseToExpire(Date from, Date to);

    @Query("select ar "
            + "from AdministrativeRequest ar "
            + "where ar.accepted = false "
            + "order by ar.maxAcceptanceDate desc")
    Collection<AdministrativeRequest> findRejectedAdministrativeRequest();

    @Query("select cr "
            + "from CollaborationRequest cr "
            + "where cr.accepted = false "
            + "order by cr.maxAcceptanceDate desc")
    Collection<CollaborationRequest> findRejectedComercialRequest();

    @Query("select avg(cr.benefitsPercentage), "
            + "sqrt(sum(cr.benefitsPercentage*cr.benefitsPercentage)/count(cr.benefitsPercentage) "
            + "- avg(cr.benefitsPercentage)*avg(cr.benefitsPercentage)) "
            + "from CollaborationRequest cr "
            + "where cr.accepted = true")
    Collection<Object> findAvgAndDevPerncentOfferProffitOnAceptedColaborationRequests();

    @Query("select avg(cr.benefitsPercentage), "
            + "sqrt(sum(cr.benefitsPercentage*cr.benefitsPercentage)/count(cr.benefitsPercentage) "
            + "- avg(cr.benefitsPercentage)*avg(cr.benefitsPercentage)) "
            + "from CollaborationRequest cr "
            + "where cr.accepted = false")
    Collection<Object> findAvgAndDevPerncentOfferProffitOnRejectedColaborationRequests();

*/