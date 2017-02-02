//
//  FabricTwitterKit.m
//  FabricTwitterKit
//
//  Created by Trevor Porter on 8/1/16.
//  Copyright © 2016 Trevor Porter. All rights reserved.
//
//  Modifications:
//  Copyright (C) 2016 Sony Interactive Entertainment Inc.
//  Licensed under the MIT License. See the LICENSE file in the project root for license information.

#import "FabricTwitterKit.h"
#import "RCTBridgeModule.h"
#import "RCTEventDispatcher.h"
#import "RCTBridge.h"
//#import <Crashlytics/Crashlytics.h>
#import <TwitterKit/TwitterKit.h>

@implementation FabricTwitterKit
@synthesize bridge = _bridge;

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(login:(RCTResponseSenderBlock)callback)
{
    [[Twitter sharedInstance] logInWithCompletion:^(TWTRSession *session, NSError *error) {
        if (session) {
            NSDictionary *body = @{@"authToken": session.authToken,
                                   @"authTokenSecret": session.authTokenSecret,
                                   @"userID":session.userID,
                                   @"userName":session.userName};
            callback(@[[NSNull null], body]);
        } else {
            NSLog(@"error: %@", [error localizedDescription]);
            callback(@[[error localizedDescription]]);
        }
    }];
}

RCT_EXPORT_METHOD(fetchProfile:(RCTResponseSenderBlock)callback)
{
    TWTRAPIClient *client = [[TWTRAPIClient alloc] init];
    TWTRSessionStore *store = [[Twitter sharedInstance] sessionStore];

    TWTRSession *lastSession = store.session;

    if(lastSession) {
        NSString *showEndpoint = @"https://api.twitter.com/1.1/users/show.json";
        NSDictionary *params = @{@"user_id": lastSession.userID};

        NSError *clientError;
        NSURLRequest *request = [client
                                 URLRequestWithMethod:@"GET"
                                 URL:showEndpoint
                                 parameters:params
                                 error:&clientError];

          if (request) {
            [client
             sendTwitterRequest:request
             completion:^(NSURLResponse *response,
                          NSData *data,
                          NSError *connectionError) {
                 if (data) {
                     // handle the response data e.g.
                     NSError *jsonError;
                     NSDictionary *json = [NSJSONSerialization
                                           JSONObjectWithData:data
                                           options:0
                                           error:&jsonError];
                     NSLog(@"%@",[json description]);
                     callback(@[[NSNull null], json]);
                 }
                 else {
                     NSLog(@"Error code: %ld | Error description: %@", (long)[connectionError code], [connectionError localizedDescription]);
                     callback(@[[connectionError localizedDescription]]);
                 }
             }];
        }
        else {
            NSLog(@"Error: %@", clientError);
        }

    }
    else {
      callback(@[@"Session must not be null."]);
    }

}

RCT_EXPORT_METHOD(fetchTweet:(NSDictionary *)options :(RCTResponseSenderBlock)callback)
{
    TWTRAPIClient *client = [[TWTRAPIClient alloc] init];
    TWTRSessionStore *store = [[Twitter sharedInstance] sessionStore];
    NSString *id = options[@"id"];
    NSString *trim_user = options[@"trim_user"];
    NSString *include_my_retweet = options[@"include_my_retweet"];

    TWTRSession *lastSession = store.session;

    if(lastSession) {
        NSString *showEndpoint = @"https://api.twitter.com/1.1/statuses/show.json";
        NSDictionary *params = @{
                                    @"id": id,
                                    @"trim_user": trim_user,
                                    @"include_my_retweet": include_my_retweet
                                };

        NSError *clientError;
        NSURLRequest *request = [client
                                 URLRequestWithMethod:@"GET"
                                 URL:showEndpoint
                                 parameters:params
                                 error:&clientError];

        if (request) {
            [client
             sendTwitterRequest:request
             completion:^(NSURLResponse *response,
                          NSData *data,
                          NSError *connectionError) {
                 if (data) {
                     // handle the response data e.g.
                     NSError *jsonError;
                     NSDictionary *json = [NSJSONSerialization
                                           JSONObjectWithData:data
                                           options:0
                                           error:&jsonError];
                     NSLog(@"%@",[json description]);
                     callback(@[[NSNull null], json]);
                 }
                 else {
                     NSLog(@"Error code: %ld | Error description: %@", (long)[connectionError code], [connectionError localizedDescription]);
                     callback(@[[connectionError localizedDescription]]);
                 }
             }];
        }
        else {
            NSLog(@"Error: %@", clientError);
        }

    }
    else {
      callback(@[@"Session must not be null."]);
    }

}

RCT_EXPORT_METHOD(composeTweet:(NSDictionary *)options :(RCTResponseSenderBlock)callback) {

    NSString *body = options[@"body"];

    TWTRComposer *composer = [[TWTRComposer alloc] init];

    if (body) {
        [composer setText:body];
    }

    UIViewController *rootView = [UIApplication sharedApplication].keyWindow.rootViewController;
    [composer showFromViewController:rootView completion:^(TWTRComposerResult result) {

        bool completed = NO, cancelled = NO, error = NO;

        if (result == TWTRComposerResultCancelled) {
            cancelled = YES;
        }
        else {
            completed = YES;
        }

        callback(@[@(completed), @(cancelled), @(error)]);

    }];
}

RCT_EXPORT_METHOD(logOut)
{
    TWTRSessionStore *store = [[Twitter sharedInstance] sessionStore];
    NSString *userID = store.session.userID;

    [store logOutUserID:userID];
}


- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

@end
