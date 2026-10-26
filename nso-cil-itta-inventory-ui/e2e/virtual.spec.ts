import { expect, test } from '@playwright/test';

test.describe('Virtual and Cloud', () => {
  test('Vnf list renders', async ({ page }) => {
    await page.goto('/virtual/vnfs');
    await expect(page.getByRole('heading', { name: 'Vnfs' }).or(page.getByRole('heading', { name: 'Vnfes' }))).toBeVisible();
  });

  test('Vnf create form is reachable', async ({ page }) => {
    await page.goto('/virtual/vnfs/new');
    await expect(page.getByRole('form', { name: 'Vnf form' })).toBeVisible();
  });

  test('Hypervisor list renders', async ({ page }) => {
    await page.goto('/virtual/hypervisors');
    await expect(page.getByRole('heading', { name: 'Hypervisors' }).or(page.getByRole('heading', { name: 'Hypervisores' }))).toBeVisible();
  });

  test('Hypervisor create form is reachable', async ({ page }) => {
    await page.goto('/virtual/hypervisors/new');
    await expect(page.getByRole('form', { name: 'Hypervisor form' })).toBeVisible();
  });

  test('Virtual Machine list renders', async ({ page }) => {
    await page.goto('/virtual/virtual-machines');
    await expect(page.getByRole('heading', { name: 'Virtual Machines' }).or(page.getByRole('heading', { name: 'Virtual Machinees' }))).toBeVisible();
  });

  test('Virtual Machine create form is reachable', async ({ page }) => {
    await page.goto('/virtual/virtual-machines/new');
    await expect(page.getByRole('form', { name: 'Virtual Machine form' })).toBeVisible();
  });

  test('Cloud Account list renders', async ({ page }) => {
    await page.goto('/virtual/cloud-accounts');
    await expect(page.getByRole('heading', { name: 'Cloud Accounts' }).or(page.getByRole('heading', { name: 'Cloud Accountes' }))).toBeVisible();
  });

  test('Cloud Account create form is reachable', async ({ page }) => {
    await page.goto('/virtual/cloud-accounts/new');
    await expect(page.getByRole('form', { name: 'Cloud Account form' })).toBeVisible();
  });

  test('K8s Cluster list renders', async ({ page }) => {
    await page.goto('/virtual/k8s-clusters');
    await expect(page.getByRole('heading', { name: 'K8s Clusters' }).or(page.getByRole('heading', { name: 'K8s Clusteres' }))).toBeVisible();
  });

  test('K8s Cluster create form is reachable', async ({ page }) => {
    await page.goto('/virtual/k8s-clusters/new');
    await expect(page.getByRole('form', { name: 'K8s Cluster form' })).toBeVisible();
  });
});
