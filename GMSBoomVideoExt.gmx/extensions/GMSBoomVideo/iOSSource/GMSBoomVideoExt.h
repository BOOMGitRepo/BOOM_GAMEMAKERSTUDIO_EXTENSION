//
//  GMSBoomVideoExt.h
//  GMSBoomVideoExt
//
//  Created by Algene Pulido on 11/2/15.
//  Copyright (c) 2015 Algene Pulido. All rights reserved.
//

#import "BMResourceManager.h"

@interface GMSBoomVideoExt : NSObject <BoomVideoTrackerDelegate>
{
}

@property (nonatomic,copy) NSString* BOOMGUID;

- (void) BOOM_init:(NSString*) guid;
- (void) BOOM_showPreroll;
- (void) BOOM_showRewards;
- (void) BOOM_showOfferList;

@end
