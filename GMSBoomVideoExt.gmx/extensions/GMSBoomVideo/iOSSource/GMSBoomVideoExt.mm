//
//  GMSBoomVideoExt.m
//  GMSBoomVideoExt
//
//  Created by Algene Pulido on 11/2/15.
//  Copyright (c) 2015 Algene Pulido. All rights reserved.
//

#import "GMSBoomVideoExt.h"
#include <asl.h>
#include <stdio.h>

@implementation GMSBoomVideoExt

//@synthesize BOOMGUID=BOOMGUID;

const int EVENT_OTHER_SOCIAL = 70;
extern int CreateDsMap( int _num, ... );
extern void CreateAsynEventWithDSMap(int dsmapindex, int event_index);

extern UIViewController *g_controller;
extern UIView *g_glView;

- (void) BOOM_init:(NSString *)guid
{
    [self setBOOMGUID:guid];
    [BMResourceManager sharedInstance].videoTrackerInfoDelegate = self;
}

- (void) BOOM_showOfferList
{
    
    [[BMResourceManager sharedInstance] showVideoForGUID:[self BOOMGUID]
                                                withType:BMOfferList];
}

- (void) BOOM_showPreroll
{
    [[BMResourceManager sharedInstance] showVideoForGUID:[self BOOMGUID]
                                                withType:BMPreroll];
}

- (void) BOOM_showRewards
{
    [[BMResourceManager sharedInstance] showVideoForGUID:[self BOOMGUID]
                                                withType:BMReward];
}

- (void)boomVideoTrackCallbackWithEvent:(BOOMEventCode)eventCode withData:(NSDictionary *)detailData {
    
    switch (eventCode) {
        case kAdViewLoaded:
        {
            /* SDK will notify this result when BOOM ad view has been loaded or
             presented on the application screen. If you're using analytics package to
             track impressions, then you can place the call to record the number of
             impressions here.*/
            NSLog(@"Ad View is Loaded on Screen");
            int dsMapIndex = CreateDsMap(2,"type", 0.0, "kAdViewLoaded",
                                         "value", 1, (void*)NULL);
            CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_SOCIAL);
            
            NSLog(@"yoyo: %@",@"Boom: Ad View is Loaded on Screen");
        }
            break;
        case kAdViewClosed:
        {
            /* SDK will notify this result when BOOM ad view has closed or dismissed
             from application screen. Your app can use it to resume suspended actions or
             perform any other work necessary to make itself ready for further.*/
             NSLog(@"Ad View is Removed from Screen");
             int dsMapIndex = CreateDsMap(2,"type", 0.0, "kAdViewClosed",
             "value", 1, (void*)NULL);
             CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_SOCIAL);
             
             NSLog(@"yoyo: %@",@"Boom: Ad View is Removed from Screen");
        }
            break;
        case kAdFailed:
        {
            /* SDK will notify this result when an ad request fails to respond with an
             ad for various reasons.*/
             NSLog(@"Ad Failed to Load");
             // errorInfo indicates that which type of failure occurred. We can easily get error Info as shown below:
             NSString *errorInfo = [detailData valueForKey:kErrorInfo];
             
             // errorDescription is optional and it can be used for debugging purpose for getting more information about error. We can easily get error Description as shown below:
             //            NSString *errorDescription = [detailData valueForKey:kErrorDescription];
             
             if ([errorInfo isEqualToString:kNoFill]) {
                 /* NoFill error states that ad failed to load as the server had no matching
                  ad in its inventory to serve for this request.*/
                 NSLog(@"Ad Not Available at the moment");
                 int dsMapIndex = CreateDsMap(2,"type", 0.0, "kNoFill",
                                              "value", 1, (void*)NULL);
                 CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_SOCIAL);
                 
                 NSLog(@"yoyo: %@",@"Boom: Ad Not Available at the moment");
             
             } else if ([errorInfo isEqualToString:kNetworkError]) {
                 /* NetworkError states that the ad failed to load due to absence of the
                  network connectivity.*/
                 NSLog(@"Network Not Available");
                 int dsMapIndex = CreateDsMap(2,"type", 0.0, "kNetworkError",
                                              "value", 1, (void*)NULL);
                 CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_SOCIAL);
                 
                 NSLog(@"yoyo: %@",@"Boom: Network Not Available");
             } else if ([errorInfo isEqualToString:kInternalError]) {
                 /* InternalError states that an invalid response was received from the
                  server. Extract the exact cause for failure using “errorDescription”
                  above.*/
                 NSLog(@"Internal Error, Not able to serve the Ad");
                 int dsMapIndex = CreateDsMap(2,"type", 0.0, "kInternalError",
                                              "value", 1, (void*)NULL);
                 CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_SOCIAL);
                 
                 NSLog(@"yoyo: %@",@"Boom: Internal Error, Not able to serve the Ad");
             }
        }
            break;

        case kPointsRevealed:
        {
            // This option is applicable only for Reward and Offer List.
            /* SDK will give this result code when points are released when user
             complete any task.*/
            NSLog(@"points Revealed: %@",[detailData valueForKey:kGetPoints]);
            int dsMapIndex = CreateDsMap(2,"type", 0.0, "kPointsRevealed",
                                         "value", 1, (void*)NULL);
            CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_SOCIAL);
            
            NSLog(@"yoyo: %@ %@",@"Boom: Points Revealed: ",[detailData valueForKey:kGetPoints]);
        }
            break;
        
            //  Optional callback event codes
        case kSuccessfulSharedOnFacebook:
        {
            NSLog(@"Ad Shared On Facebook");
            int mapIndex = CreateDsMap(2,"type", 0.0, "kSuccessfulSharedOnFacebook",
                                         "value", 1, (void*)NULL);
            CreateAsynEventWithDSMap(mapIndex, EVENT_OTHER_SOCIAL);
            
            NSLog(@"yoyo: %@",@"Boom: Ad Shared On Facebook");
        }
            break;
        
        case kSuccessfulSharedOnTwitter:
        {
            NSLog(@"Ad Shared On Twitter");
            int mIndex = CreateDsMap(2,"type", 0.0, "kSuccessfulSharedOnTwitter",
                                       "value", 1, (void*)NULL);
            CreateAsynEventWithDSMap(mIndex, EVENT_OTHER_SOCIAL);
            
            NSLog(@"yoyo: %@",@"Boom: Ad Shared On Twitter");
        }
            break;
        
        case kSuccessfulSharedOnGooglePlus:
        {
            NSLog(@"Ad Shared On Google Plus");
            int mIn = CreateDsMap(2,"type", 0.0, "kSuccessfulSharedOnGooglePlus",
                                       "value", 1, (void*)NULL);
            CreateAsynEventWithDSMap(mIn, EVENT_OTHER_SOCIAL);
            
            NSLog(@"yoyo: %@",@"Boom: Ad Shared On Google Plus");
        }
            break;
        
        default:
            break;
    }
}

@end
