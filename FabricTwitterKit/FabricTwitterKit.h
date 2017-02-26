//
//  FabricTwitterKit.h
//  FabricTwitterKit
//
//  Created by Trevor Porter on 8/1/16.
//  Copyright © 2016 Trevor Porter. All rights reserved.
//

#import <React/RCTBridgeModule.h>

@interface FabricTwitterKit : NSObject <RCTBridgeModule> {
    RCTResponseSenderBlock _callback;
}

@end
