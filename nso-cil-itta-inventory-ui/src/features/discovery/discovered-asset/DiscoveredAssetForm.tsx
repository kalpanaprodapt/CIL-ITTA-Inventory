import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { DISCOVERED_ASSET_LIFECYCLE_STAGE_OPTIONS, DiscoveredAssetLifecycleStage } from './types';
import { validateDiscoveredAsset, cleanDiscoveredAssetValues } from './schema';
import { DISCOVEREDASSET_DEFAULTS } from './types';
import type { DiscoveredAssetFormValues, DiscoveredAssetInput } from './types';

interface Props {
  initial?: DiscoveredAssetFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: DiscoveredAssetInput) => void;
  onCancel?: () => void;
}

export function DiscoveredAssetForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<DiscoveredAssetFormValues>({ ...DISCOVEREDASSET_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof DiscoveredAssetFormValues>(key: K, value: DiscoveredAssetFormValues[K]) {
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
    const result = validateDiscoveredAsset(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanDiscoveredAssetValues(values as Record<string, unknown>) as unknown as DiscoveredAssetInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Discovered Asset form">
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
      <FormField label="Job Id" htmlFor="jobId" error={errors.jobId} required={true}>
        <Input
          id="jobId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.jobId ?? ''}
          onChange={(v) => set('jobId', v)}
        />
      </FormField>
      <FormField label="Asset Type" htmlFor="assetType" error={errors.assetType} required={true}>
        <Input
          id="assetType"
          value={values.assetType ?? ''}
          maxLength={255}
          onChange={(v) => set('assetType', v)}
        />
      </FormField>
      <FormField label="Fingerprint" htmlFor="fingerprint" error={errors.fingerprint} required={true}>
        <Input
          id="fingerprint"
          value={values.fingerprint ?? ''}
          maxLength={255}
          onChange={(v) => set('fingerprint', v)}
        />
      </FormField>
      <FormField label="Raw Payload" htmlFor="rawPayload" error={errors.rawPayload} required={false}>
        <TextArea
          id="rawPayload"
          value={values.rawPayload ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('rawPayload', v)}
        />
      </FormField>
      <FormField label="Matched Asset Id" htmlFor="matchedAssetId" error={errors.matchedAssetId} required={false}>
        <Input
          id="matchedAssetId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.matchedAssetId ?? ''}
          onChange={(v) => set('matchedAssetId', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
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
          options={DISCOVERED_ASSET_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as DiscoveredAssetLifecycleStage | undefined)}
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
