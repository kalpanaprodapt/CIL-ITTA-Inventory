import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { COUNTRY_PATH } from './types';
import type { Country, CountryFilter, CountryInput, CountrySummary, CountryUpdateInput } from './types';
import { toCountryQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCountrys(
  filter: CountryFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Country>> {
  return http.get<Page<Country>>(COUNTRY_PATH, { ...toCountryQuery(filter), page, size, sort });
}

export function getCountry(id: string): Promise<Country> {
  return http.get<Country>(`${COUNTRY_PATH}/${id}`);
}

export function getCountryByCode(code: string): Promise<Country> {
  return http.get<Country>(`${COUNTRY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCountrySummaries(status?: LifecycleStatus): Promise<CountrySummary[]> {
  return http.get<CountrySummary[]>(`${COUNTRY_PATH}/summaries`, { status });
}

export function createCountry(input: CountryInput): Promise<Country> {
  return http.post<Country>(COUNTRY_PATH, input);
}

export function updateCountry(id: string, input: CountryUpdateInput): Promise<Country> {
  return http.put<Country>(`${COUNTRY_PATH}/${id}`, input);
}

export function changeCountryStatus(id: string, status: LifecycleStatus): Promise<Country> {
  return http.patch<Country>(`${COUNTRY_PATH}/${id}/status`, undefined, { status });
}

export function deleteCountry(id: string): Promise<void> {
  return http.delete(`${COUNTRY_PATH}/${id}`);
}

export function bulkCreateCountrys(inputs: CountryInput[]): Promise<BulkResult<Country>> {
  return http.post<BulkResult<Country>>(`${COUNTRY_PATH}/bulk`, inputs);
}

export function getCountryStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${COUNTRY_PATH}/stats/status`);
}

export function exportCountrysCsv(filter: CountryFilter): Promise<Blob> {
  return http.blob(`${COUNTRY_PATH}/export`, toCountryQuery(filter));
}
