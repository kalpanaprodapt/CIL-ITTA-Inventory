import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SPLICE_LIFECYCLE_STAGE_OPTIONS, SPLICE_CRITICALITY_OPTIONS, SpliceLifecycleStage, SpliceCriticality } from './types';
import { validateSplice, cleanSpliceValues } from './schema';
import { SPLICE_DEFAULTS } from './types';
import type { SpliceFormValues, SpliceInput } from './types';

interface Props {
  initial?: SpliceFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: SpliceInput) => void;
  onCancel?: () => void;
}

export function SpliceForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<SpliceFormValues>({ ...SPLICE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof SpliceFormValues>(key: K, value: SpliceFormValues[K]) {
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
    const result = validateSplice(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanSpliceValues(values as Record<string, unknown>) as unknown as SpliceInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Splice form">
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
      <FormField label="Enclosure Id" htmlFor="enclosureId" error={errors.enclosureId} required={true}>
        <Input
          id="enclosureId"
          value={values.enclosureId ?? ''}
          maxLength={255}
          onChange={(v) => set('enclosureId', v)}
        />
      </FormField>
      <FormField label="A Strand Id" htmlFor="aStrandId" error={errors.aStrandId} required={false}>
        <Input
          id="aStrandId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.aStrandId ?? ''}
          onChange={(v) => set('aStrandId', v)}
        />
      </FormField>
      <FormField label="Z Strand Id" htmlFor="zStrandId" error={errors.zStrandId} required={false}>
        <Input
          id="zStrandId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.zStrandId ?? ''}
          onChange={(v) => set('zStrandId', v)}
        />
      </FormField>
      <FormField label="Loss Db" htmlFor="lossDb" error={errors.lossDb} required={false}>
        <NumberInput
          id="lossDb"
          value={values.lossDb}
          step={0.0001}
          onChange={(v) => set('lossDb', v)}
        />
      </FormField>
      <FormField label="Spliced On" htmlFor="splicedOn" error={errors.splicedOn} required={false}>
        <DateInput
          id="splicedOn"
          value={values.splicedOn ?? ''}
          onChange={(v) => set('splicedOn', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={SPLICE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as SpliceLifecycleStage | undefined)}
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
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={SPLICE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as SpliceCriticality | undefined)}
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
