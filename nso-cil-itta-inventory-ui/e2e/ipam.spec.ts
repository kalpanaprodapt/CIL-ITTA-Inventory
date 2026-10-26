import { expect, test } from '@playwright/test';

test.describe('IP Management', () => {
  test('Ip Subnet list renders', async ({ page }) => {
    await page.goto('/ipam/ip-subnets');
    await expect(page.getByRole('heading', { name: 'Ip Subnets' }).or(page.getByRole('heading', { name: 'Ip Subnetes' }))).toBeVisible();
  });

  test('Ip Subnet create form is reachable', async ({ page }) => {
    await page.goto('/ipam/ip-subnets/new');
    await expect(page.getByRole('form', { name: 'Ip Subnet form' })).toBeVisible();
  });

  test('Ip Address list renders', async ({ page }) => {
    await page.goto('/ipam/ip-addresses');
    await expect(page.getByRole('heading', { name: 'Ip Addresss' }).or(page.getByRole('heading', { name: 'Ip Addresses' }))).toBeVisible();
  });

  test('Ip Address create form is reachable', async ({ page }) => {
    await page.goto('/ipam/ip-addresses/new');
    await expect(page.getByRole('form', { name: 'Ip Address form' })).toBeVisible();
  });

  test('Ip Pool list renders', async ({ page }) => {
    await page.goto('/ipam/ip-pools');
    await expect(page.getByRole('heading', { name: 'Ip Pools' }).or(page.getByRole('heading', { name: 'Ip Pooles' }))).toBeVisible();
  });

  test('Ip Pool create form is reachable', async ({ page }) => {
    await page.goto('/ipam/ip-pools/new');
    await expect(page.getByRole('form', { name: 'Ip Pool form' })).toBeVisible();
  });

  test('Vrf Instance list renders', async ({ page }) => {
    await page.goto('/ipam/vrf-instances');
    await expect(page.getByRole('heading', { name: 'Vrf Instances' }).or(page.getByRole('heading', { name: 'Vrf Instancees' }))).toBeVisible();
  });

  test('Vrf Instance create form is reachable', async ({ page }) => {
    await page.goto('/ipam/vrf-instances/new');
    await expect(page.getByRole('form', { name: 'Vrf Instance form' })).toBeVisible();
  });

  test('Dns Record list renders', async ({ page }) => {
    await page.goto('/ipam/dns-records');
    await expect(page.getByRole('heading', { name: 'Dns Records' }).or(page.getByRole('heading', { name: 'Dns Recordes' }))).toBeVisible();
  });

  test('Dns Record create form is reachable', async ({ page }) => {
    await page.goto('/ipam/dns-records/new');
    await expect(page.getByRole('form', { name: 'Dns Record form' })).toBeVisible();
  });
});
