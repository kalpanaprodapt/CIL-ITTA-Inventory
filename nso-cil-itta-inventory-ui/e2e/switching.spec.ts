import { expect, test } from '@playwright/test';

test.describe('L2 and L3 Routing', () => {
  test('Vlan list renders', async ({ page }) => {
    await page.goto('/switching/vlans');
    await expect(page.getByRole('heading', { name: 'Vlans' }).or(page.getByRole('heading', { name: 'Vlanes' }))).toBeVisible();
  });

  test('Vlan create form is reachable', async ({ page }) => {
    await page.goto('/switching/vlans/new');
    await expect(page.getByRole('form', { name: 'Vlan form' })).toBeVisible();
  });

  test('Vlan Pool list renders', async ({ page }) => {
    await page.goto('/switching/vlan-pools');
    await expect(page.getByRole('heading', { name: 'Vlan Pools' }).or(page.getByRole('heading', { name: 'Vlan Pooles' }))).toBeVisible();
  });

  test('Vlan Pool create form is reachable', async ({ page }) => {
    await page.goto('/switching/vlan-pools/new');
    await expect(page.getByRole('form', { name: 'Vlan Pool form' })).toBeVisible();
  });

  test('Route Target list renders', async ({ page }) => {
    await page.goto('/switching/route-targets');
    await expect(page.getByRole('heading', { name: 'Route Targets' }).or(page.getByRole('heading', { name: 'Route Targetes' }))).toBeVisible();
  });

  test('Route Target create form is reachable', async ({ page }) => {
    await page.goto('/switching/route-targets/new');
    await expect(page.getByRole('form', { name: 'Route Target form' })).toBeVisible();
  });

  test('Bgp Session list renders', async ({ page }) => {
    await page.goto('/switching/bgp-sessions');
    await expect(page.getByRole('heading', { name: 'Bgp Sessions' }).or(page.getByRole('heading', { name: 'Bgp Sessiones' }))).toBeVisible();
  });

  test('Bgp Session create form is reachable', async ({ page }) => {
    await page.goto('/switching/bgp-sessions/new');
    await expect(page.getByRole('form', { name: 'Bgp Session form' })).toBeVisible();
  });

  test('Ospf Area list renders', async ({ page }) => {
    await page.goto('/switching/ospf-areas');
    await expect(page.getByRole('heading', { name: 'Ospf Areas' }).or(page.getByRole('heading', { name: 'Ospf Areaes' }))).toBeVisible();
  });

  test('Ospf Area create form is reachable', async ({ page }) => {
    await page.goto('/switching/ospf-areas/new');
    await expect(page.getByRole('form', { name: 'Ospf Area form' })).toBeVisible();
  });
});
