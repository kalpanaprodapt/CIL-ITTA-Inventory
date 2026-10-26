import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SITE_SITE_TYPE_OPTIONS, SITE_LIFECYCLE_STAGE_OPTIONS, SiteSiteType, SiteLifecycleStage } from './types';
import { validateSite, cleanSiteValues } from './schema';
import { SITE_DEFAULTS } from './types';
import type { SiteFormValues, SiteInput } from './types';

interface Props {
  initial?: SiteFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: SiteInput) => void;
  onCancel?: () => void;
}

export function SiteForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<SiteFormValues>({ ...SITE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof SiteFormValues>(key: K, value: SiteFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateSite(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanSiteValues(values as Record<string, unknown>) as unknown as SiteInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Site form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="City Id" htmlFor="cityId" error={errors.cityId} required={true}>
        <Input
          id="cityId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.cityId ?? ''}
          onChange={(v) => set('cityId', v)}
        />
      </FormField>
      <FormField label="Site Type" htmlFor="siteType" error={errors.siteType} required={false}>
        <Select
          id="siteType"
          value={values.siteType ?? ''}
          options={SITE_SITE_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('siteType', (v || undefined) as SiteSiteType | undefined)}
        />
      </FormField>
      <FormField label="Address" htmlFor="address" error={errors.address} required={false}>
        <Input
          id="address"
          value={values.address ?? ''}
          maxLength={255}
          onChange={(v) => set('address', v)}
        />
      </FormField>
      <FormField label="Latitude" htmlFor="latitude" error={errors.latitude} required={false}>
        <NumberInput
          id="latitude"
          value={values.latitude}
          step={0.0001}
          onChange={(v) => set('latitude', v)}
        />
      </FormField>
      <FormField label="Longitude" htmlFor="longitude" error={errors.longitude} required={false}>
        <NumberInput
          id="longitude"
          value={values.longitude}
          step={0.0001}
          onChange={(v) => set('longitude', v)}
        />
      </FormField>
      <FormField label="Power Kw" htmlFor="powerKw" error={errors.powerKw} required={false}>
        <NumberInput
          id="powerKw"
          value={values.powerKw}
          step={0.0001}
          onChange={(v) => set('powerKw', v)}
        />
      </FormField>
      <FormField label="Access Hours" htmlFor="accessHours" error={errors.accessHours} required={false}>
        <Input
          id="accessHours"
          value={values.accessHours ?? ''}
          maxLength={255}
          onChange={(v) => set('accessHours', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={SITE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as SiteLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
