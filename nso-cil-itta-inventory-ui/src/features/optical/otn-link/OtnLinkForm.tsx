import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { OTN_LINK_ODU_OPTIONS, OTN_LINK_LIFECYCLE_STAGE_OPTIONS, OtnLinkOdu, OtnLinkLifecycleStage } from './types';
import { validateOtnLink, cleanOtnLinkValues } from './schema';
import { OTNLINK_DEFAULTS } from './types';
import type { OtnLinkFormValues, OtnLinkInput } from './types';

interface Props {
  initial?: OtnLinkFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: OtnLinkInput) => void;
  onCancel?: () => void;
}

export function OtnLinkForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<OtnLinkFormValues>({ ...OTNLINK_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof OtnLinkFormValues>(key: K, value: OtnLinkFormValues[K]) {
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
    const result = validateOtnLink(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanOtnLinkValues(values as Record<string, unknown>) as unknown as OtnLinkInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Otn Link form">
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
      <FormField label="A Node Id" htmlFor="aNodeId" error={errors.aNodeId} required={true}>
        <Input
          id="aNodeId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.aNodeId ?? ''}
          onChange={(v) => set('aNodeId', v)}
        />
      </FormField>
      <FormField label="Z Node Id" htmlFor="zNodeId" error={errors.zNodeId} required={true}>
        <Input
          id="zNodeId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.zNodeId ?? ''}
          onChange={(v) => set('zNodeId', v)}
        />
      </FormField>
      <FormField label="Odu" htmlFor="odu" error={errors.odu} required={false}>
        <Select
          id="odu"
          value={values.odu ?? ''}
          options={OTN_LINK_ODU_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('odu', (v || undefined) as OtnLinkOdu | undefined)}
        />
      </FormField>
      <FormField label="Latency Us" htmlFor="latencyUs" error={errors.latencyUs} required={false}>
        <NumberInput
          id="latencyUs"
          value={values.latencyUs}
          step={0.0001}
          onChange={(v) => set('latencyUs', v)}
        />
      </FormField>
      <FormField label="Protection" htmlFor="protection" error={errors.protection} required={false}>
        <Input
          id="protection"
          value={values.protection ?? ''}
          maxLength={255}
          onChange={(v) => set('protection', v)}
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
          options={OTN_LINK_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as OtnLinkLifecycleStage | undefined)}
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
