import { TestBed } from '@angular/core/testing';

import { RouterNavigationService } from './router-navigation.service';

describe('RouterNavigationService', () => {
  let service: RouterNavigationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouterNavigationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
