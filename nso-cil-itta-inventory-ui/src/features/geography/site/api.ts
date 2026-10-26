import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SITE_PATH } from './types';
import type { Site, SiteFilter, SiteInput, SiteSummary, SiteUpdateInput } from './types';
import { toSiteQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSites(
  filter: SiteFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Site>> {
  return http.get<Page<Site>>(SITE_PATH, { ...toSiteQuery(filter), page, size, sort });
}

export function getSite(id: string): Promise<Site> {
  return http.get<Site>(`${SITE_PATH}/${id}`);
}

export function getSiteByCode(code: string): Promise<Site> {
  return http.get<Site>(`${SITE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSiteSummaries(status?: LifecycleStatus): Promise<SiteSummary[]> {
  return http.get<SiteSummary[]>(`${SITE_PATH}/summaries`, { status });
}

export function createSite(input: SiteInput): Promise<Site> {
  return http.post<Site>(SITE_PATH, input);
}

export function updateSite(id: string, input: SiteUpdateInput): Promise<Site> {
  return http.put<Site>(`${SITE_PATH}/${id}`, input);
}

export function changeSiteStatus(id: string, status: LifecycleStatus): Promise<Site> {
  return http.patch<Site>(`${SITE_PATH}/${id}/status`, undefined, { status });
}

export function deleteSite(id: string): Promise<void> {
  return http.delete(`${SITE_PATH}/${id}`);
}

export function bulkCreateSites(inputs: SiteInput[]): Promise<BulkResult<Site>> {
  return http.post<BulkResult<Site>>(`${SITE_PATH}/bulk`, inputs);
}

export function getSiteStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SITE_PATH}/stats/status`);
}

export function exportSitesCsv(filter: SiteFilter): Promise<Blob> {
  return http.blob(`${SITE_PATH}/export`, toSiteQuery(filter));
}

export function listSiteByCityId(cityId: string, page = 0, size = 20): Promise<Page<Site>> {
  return http.get<Page<Site>>(`${SITE_PATH}/by-city-id/${cityId}`, { page, size });
}
