import { TestBed } from '@angular/core/testing';

import { UserAPIService } from './user-data.service';

describe('UserDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserAPIService = TestBed.get(UserAPIService);
    expect(service).toBeTruthy();
  });
});
