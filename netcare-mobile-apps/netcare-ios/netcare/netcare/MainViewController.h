//
//  MainViewController.h
//  netcare
//
// Copyright (C) 2012 Callista Enterprise AB <info@callistaenterprise.se>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

#import "FlipsideViewController.h"
#import "HTTPAuthentication.h"

@interface MainViewController : UIViewController <FlipsideViewControllerDelegate, HTTPConnectionDelegate, HTTPAuthenticationDelegate>

@property (weak, nonatomic) IBOutlet UITextField *personNumberTextEdit;
@property (weak, nonatomic) IBOutlet UITextField *pinCodeTextEdit;
// nextpage button is not really used, just keeps 
// a point to maintain a storyboard sequence, which really is invoked
// upon a proper authenitcation
@property (weak, nonatomic) IBOutlet UIButton *nextPageButton;

- (IBAction)login:(id)sender;
- (IBAction)textFieldReturn:(id)sender;
- (IBAction)backgroundTouched:(id)sender;

@end