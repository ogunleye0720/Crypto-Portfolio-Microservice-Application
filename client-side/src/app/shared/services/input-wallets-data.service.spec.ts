import { TestBed } from '@angular/core/testing';

import { InputWalletsDataService } from './input-wallets-data.service';

let service: InputWalletsDataService;

beforeEach(() => {
  TestBed.configureTestingModule({});
  service = TestBed.inject(InputWalletsDataService);
  sessionStorage.clear();
});

describe('InputWalletsDataService', () => {
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

describe('StoreDataInSessionStorage', () => {
  it('should store data in sessionStorage', () => {
    const data = 'test data';
    service.storeDataInSessionStorage(data);
    expect(sessionStorage.getItem('input-wallets-data')).toEqual('JSON.stringify(data)');
  });
});

describe('getDataFromSessionStorage', () => {
  it('should return data as a string when sessionStorage contains data', () => {
    const data = 'test data';
    sessionStorage.setItem('input-wallets-data', JSON.stringify(data));
    const result = service.getDataFromSessionStorage();
    expect(result).toEqual(data);
  });

  it('should return empty string when sessionStorage does not contain data', () => {
    sessionStorage.removeItem('input-wallets-data');
    const result = service.getDataFromSessionStorage();
    expect(result).toEqual('');
  });

  it('should return empty string when sessionStorage contains invalid JSON data', () => {
    sessionStorage.setItem('input-wallets-data', 'invalid JSON');
    const result = service.getDataFromSessionStorage();
    expect(result).toEqual('');
  });
});
