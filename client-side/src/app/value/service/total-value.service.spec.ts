import { TestBed } from '@angular/core/testing';

import { TotalValueService } from './total-value.service';

describe('TotalValueService', () => {
  let service: TotalValueService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TotalValueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
